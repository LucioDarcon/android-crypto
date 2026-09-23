package com.ldss.binary.protector.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ldss.binary.protector.crypto.CryptoLocal
import com.ldss.binary.protector.utils.Constants

class CryptoViewModel : ViewModel() {

    private val cryptoLocal = CryptoLocal()

    val aliasInput = MutableLiveData("dispositivo_principal")
    val plainTextInput = MutableLiveData("")

    private val _encryptedResult = MutableLiveData("")
    val encryptedResult: LiveData<String> = _encryptedResult

    private val _decryptedResult = MutableLiveData("")
    val decryptedResult: LiveData<String> = _decryptedResult

    private val _logs = MutableLiveData("")
    val logs: LiveData<String> = _logs

    private fun addLog(message: String) {
        val current = _logs.value ?: ""
        _logs.value = if (current.isEmpty()) message else "$current\n$message"
    }

    private fun getAlias(): String {
        val input = aliasInput.value?.trim().orEmpty()
        val userKey = input.ifEmpty { "dispositivo_principal" }
        return Constants.getKeyLocal(userKey)
    }

    private fun ensureKeyExists(alias: String): Boolean {
        return if (!cryptoLocal.keyExists(alias)) {
            val created = cryptoLocal.generateKeyPair(alias)
            if (created) {
                addLog("Chave KeyStore não existia. Gerada chave RSA automaticamente para alias '$alias'")
            }
            created
        } else {
            true
        }
    }

    fun onEncryptClicked() {
        val alias = getAlias()
        val text = plainTextInput.value?.trim().orEmpty()

        if (text.isEmpty()) {
            addLog("Criptografia falhou: Digite o texto simples para criptografar")
            return
        }

        if (!ensureKeyExists(alias)) {
            addLog("Criptografia falhou: Não foi possível obter a chave pública no KeyStore")
            return
        }

        try {
            val encryptedBase64 = cryptoLocal.encryptData(alias, text)
            _encryptedResult.value = encryptedBase64
            _decryptedResult.value = ""
            addLog("Criptografia realizada com Sucesso usando a Chave Pública do KeyStore!")
        } catch (e: Exception) {
            addLog("Erro na Criptografia: ${e.message}")
        }
    }

    fun onDecryptClicked() {
        val alias = getAlias()
        val cipherText = encryptedResult.value?.trim().orEmpty()

        if (cipherText.isEmpty()) {
            addLog("Decriptografia falhou: Nenhum dado criptografado disponível")
            return
        }

        try {
            val decrypted = cryptoLocal.decryptData(alias, cipherText)
            _decryptedResult.value = decrypted
            addLog("Decriptografia realizada com Sucesso usando a Chave Privada do KeyStore!")
        } catch (e: Exception) {
            addLog("Erro na Decriptografia: ${e.message}")
        }
    }
}
