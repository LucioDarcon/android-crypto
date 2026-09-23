package com.ldss.binary.protector.crypto

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CryptoLocalTest {

    private lateinit var cryptoLocal: CryptoLocal
    private val testAlias = "test_device_key_alias"

    @Before
    fun setUp() {
        cryptoLocal = CryptoLocal()
        cryptoLocal.deleteKey(testAlias)
    }

    @After
    fun tearDown() {
        cryptoLocal.deleteKey(testAlias)
    }

    @Test
    fun testGenerateKeyPair() {
        val created = cryptoLocal.generateKeyPair(testAlias)
        assertTrue("A chave deve ser criada com sucesso no KeyStore", created)
        assertTrue("A chave deve existir no KeyStore após a criação", cryptoLocal.keyExists(testAlias))
    }

    @Test
    fun testGetPublicKeyBase64() {
        cryptoLocal.generateKeyPair(testAlias)
        val publicKeyBase64 = cryptoLocal.getPublicKeyBase64(testAlias)
        assertNotNull("A chave pública exportada não deve ser nula", publicKeyBase64)
        assertTrue("A chave pública deve ser uma string Base64 não vazia", publicKeyBase64!!.isNotEmpty())
    }

    @Test
    fun testSignAndVerifyMessage() {
        cryptoLocal.generateKeyPair(testAlias)
        val message = "Mensagem de teste para assinatura digital"

        val signatureBase64 = cryptoLocal.signMessage(testAlias, message)
        assertNotNull("A assinatura não deve ser nula", signatureBase64)
        assertTrue("A assinatura Base64 não deve ser vazia", signatureBase64.isNotEmpty())

        val isValid = cryptoLocal.verifySignature(testAlias, message, signatureBase64)
        assertTrue("A verificação da assinatura deve ser válida", isValid)

        val isInvalid = cryptoLocal.verifySignature(testAlias, "Mensagem alterada", signatureBase64)
        assertFalse("A verificação da assinatura deve falhar se a mensagem for alterada", isInvalid)
    }

    @Test
    fun testEncryptAndDecryptData() {
        cryptoLocal.generateKeyPair(testAlias)
        val plainText = "Texto de teste ultrasecreto para criptografia RSA"

        val encryptedBase64 = cryptoLocal.encryptData(testAlias, plainText)
        assertNotNull("O texto criptografado não deve ser nulo", encryptedBase64)
        assertTrue("O texto criptografado deve ser não vazio", encryptedBase64.isNotEmpty())

        val decryptedText = cryptoLocal.decryptData(testAlias, encryptedBase64)
        assertEquals("O texto decriptografado deve ser idêntico ao texto original", plainText, decryptedText)
    }

    @Test
    fun testDeleteKey() {
        cryptoLocal.generateKeyPair(testAlias)
        assertTrue("A chave deve existir antes de deletar", cryptoLocal.keyExists(testAlias))

        val deleted = cryptoLocal.deleteKey(testAlias)
        assertTrue("A chave deve ser deletada com sucesso", deleted)
        assertFalse("A chave não deve mais existir no KeyStore", cryptoLocal.keyExists(testAlias))
    }
}
