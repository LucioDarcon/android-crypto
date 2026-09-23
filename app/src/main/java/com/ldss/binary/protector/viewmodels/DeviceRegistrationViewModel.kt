package com.ldss.binary.protector.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ldss.binary.protector.crypto.CryptoLocal
import com.ldss.binary.protector.utils.Constants

class DeviceRegistrationViewModel : ViewModel() {

    private val cryptoLocal = CryptoLocal()

    val aliasInput = MutableLiveData("dispositivo_principal")
    val messageInput = MutableLiveData("")

    private val _registrationStatus = MutableLiveData("Não Registrado")
    val registrationStatus: LiveData<String> = _registrationStatus

    private val _publicKeyDisplay = MutableLiveData("")
    val publicKeyDisplay: LiveData<String> = _publicKeyDisplay

    private val _signatureResult = MutableLiveData("")
    val signatureResult: LiveData<String> = _signatureResult

    private val _logs = MutableLiveData("")
    val logs: LiveData<String> = _logs

    init {
        checkRegistrationStatus()
    }

    private fun addLog(message: String) {
        val current = _logs.value ?: ""
        _logs.value = if (current.isEmpty()) message else "$current\n$message"
    }

    private fun getAlias(): String {
        val input = aliasInput.value?.trim().orEmpty()
        val userKey = input.ifEmpty { "dispositivo_principal" }
        return Constants.getKeyLocal(userKey)
    }

    fun checkRegistrationStatus() {
        val alias = getAlias()
        if (cryptoLocal.keyExists(alias)) {
            _registrationStatus.value = "Dispositivo Registrado (KeyStore OK)"
            _publicKeyDisplay.value = cryptoLocal.getPublicKeyBase64(alias) ?: ""
        } else {
            _registrationStatus.value = "Dispositivo Não Registrado"
            _publicKeyDisplay.value = ""
        }
    }

    fun onRegisterDeviceClicked() {
        val alias = getAlias()
        val success = cryptoLocal.generateKeyPair(alias)
        if (success) {
            addLog("Registrar Dispositivo: Par de chaves RSA gerado no KeyStore para alias '$alias'")
            checkRegistrationStatus()
        } else {
            addLog("Registrar Dispositivo: Falha ao gerar chaves no KeyStore")
        }
    }

    fun onDeleteRegistrationClicked() {
        val alias = getAlias()
        val success = cryptoLocal.deleteKey(alias)
        if (success) {
            addLog("Deletar Registro: Registro removido do KeyStore para alias '$alias'")
            _signatureResult.value = ""
            checkRegistrationStatus()
        } else {
            addLog("Deletar Registro: Registro não encontrado ou falha ao deletar")
        }
    }

    fun onSignMessageClicked() {
        val alias = getAlias()
        val message = messageInput.value?.trim().orEmpty()

        if (message.isEmpty()) {
            addLog("Assinar Mensagem: Digite uma mensagem para assinar")
            return
        }

        if (!cryptoLocal.keyExists(alias)) {
            addLog("Assinar Mensagem: Dispositivo não registrado. Registre o dispositivo primeiro.")
            return
        }

        try {
            val signature = cryptoLocal.signMessage(alias, message)
            _signatureResult.value = signature
            val isVerified = cryptoLocal.verifySignature(alias, message, signature)
            addLog("Assinar Mensagem: Sucesso! Assinatura gerada (Verificação local: $isVerified)")
        } catch (e: Exception) {
            addLog("Assinar Mensagem Erro: ${e.message}")
        }
    }
}
