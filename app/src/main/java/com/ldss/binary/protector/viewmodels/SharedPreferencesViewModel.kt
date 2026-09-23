package com.ldss.binary.protector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ldss.binary.protector.shared_preferences.SharedPreferencesManager

class SharedPreferencesViewModel(application: Application) : AndroidViewModel(application) {

    private val prefsManager = SharedPreferencesManager(application)

    val title: LiveData<String> = MutableLiveData("SharedPreferences")

    val key = MutableLiveData("")
    val value = MutableLiveData("")

    private val _logs = MutableLiveData("")

    val logs: LiveData<String> = _logs

    private fun addLog(message: String) {
        val currentLogs = _logs.value ?: ""
        _logs.value = if (currentLogs.isEmpty()) message else "$currentLogs\n$message"
    }

    fun onCreateClicked() {
        val currentKey = key.value?.trim().orEmpty()
        val user = currentKey.ifEmpty { "default_user" }
        prefsManager.initialize(user)
        addLog("Criar clicado: Inicializado SharedPreferences para o usuário '$user'")
    }

    fun onSaveClicked() {
        val key = key.value?.trim().orEmpty()
        val value = value.value?.trim().orEmpty()

        if (key.isEmpty()) {
            addLog("Salvar falhou: Chave não pode estar vazia")
            return
        }

        if (!prefsManager.isInitialized()) {
            prefsManager.initialize("default_user")
        }

        prefsManager.save(key, value)
        addLog("Salvar clicado: Chave '$key' salva com o valor '$value'")
    }

    fun onEditClicked() {
        val k = key.value?.trim().orEmpty()
        if (k.isEmpty()) {
            addLog("Editar falhou: Informe a chave para buscar/editar")
            return
        }

        if (!prefsManager.isInitialized()) {
            prefsManager.initialize("default_user")
        }

        val foundValue = prefsManager.getValue(k)
        if (foundValue != null) {
            value.value = foundValue
            addLog("Editar clicado: Valor carregado para a chave '$k' = '$foundValue'")
        } else {
            addLog("Editar clicado: Chave '$k' não encontrada no SharedPreferences")
        }
    }

    fun onRemoveClicked() {
        val k = key.value?.trim().orEmpty()
        if (k.isEmpty()) {
            addLog("Remover falhou: Chave não pode estar vazia")
            return
        }

        if (!prefsManager.isInitialized()) {
            prefsManager.initialize("default_user")
        }

        prefsManager.remove(k)
        value.value = ""
        addLog("Remover clicado: Chave '$k' removida do SharedPreferences")
    }
}
