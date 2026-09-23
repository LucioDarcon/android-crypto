package com.ldss.binary.protector.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SQLiteViewModel : ViewModel() {

    val title: LiveData<String> = MutableLiveData("SQLite")

    val value = MutableLiveData("")

    private val _logs = MutableLiveData("")
    val logs: LiveData<String> = _logs

    private fun addLog(message: String) {
        val currentLogs = _logs.value ?: ""
        _logs.value = if (currentLogs.isEmpty()) message else "$currentLogs\n$message"
    }

    fun onCreateClicked() {
        addLog("Criar clicado em SQLite")
    }

    fun onSaveClicked() {
        val v = value.value?.trim().orEmpty()
        addLog("Salvar clicado em SQLite: valor = '$v'")
    }

    fun onEditClicked() {
        val v = value.value?.trim().orEmpty()
        addLog("Editar clicado em SQLite: valor = '$v'")
    }

    fun onRemoveClicked() {
        value.value = ""
        addLog("Remover clicado em SQLite")
    }
}
