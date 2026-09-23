package com.ldss.binary.protector.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel()
    }

    @Test
    fun testLoginSuccess() {
        viewModel.username.value = "admin"
        viewModel.password.value = "admin"

        viewModel.onLoginClicked()

        assertEquals("", viewModel.errorMessage.value)
        assertEquals("admin", viewModel.navigateToMain.value)
    }

    @Test
    fun testLoginEmptyFields() {
        viewModel.username.value = ""
        viewModel.password.value = ""

        viewModel.onLoginClicked()

        assertEquals("Preencha todos os campos", viewModel.errorMessage.value)
    }

    @Test
    fun testLoginInvalidCredentials() {
        viewModel.username.value = "usuario_invalido"
        viewModel.password.value = "senha_errada"

        viewModel.onLoginClicked()

        assertEquals("Credenciais inválidas", viewModel.errorMessage.value)
    }
}
