package com.ldss.binary.protector.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val username = MutableLiveData("")
    val password = MutableLiveData("")

    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    private val _navigateToMain = MutableLiveData<String?>(null)
    val navigateToMain: LiveData<String?> = _navigateToMain

    fun onLoginClicked() {
        val user = username.value?.trim().orEmpty()
        val pass = password.value?.trim().orEmpty()

        if (user.isBlank() || pass.isBlank()) {
            _errorMessage.value = "Preencha todos os campos"
        } else if ((user == "admin") && (pass == "admin")) {
            _errorMessage.value = ""
            _navigateToMain.value = user
        } else {
            _errorMessage.value = "Credenciais inválidas"
        }
    }

    fun onNavigatedToMain() {
        _navigateToMain.value = null
    }
}
