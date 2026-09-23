package com.ldss.binary.protector.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val jniTitle: LiveData<String> = MutableLiveData("Security")

    private val _navigateToSharedPreferences = MutableLiveData(false)
    val navigateToSharedPreferences: LiveData<Boolean> = _navigateToSharedPreferences

    private val _navigateToSQLite = MutableLiveData(false)
    val navigateToSQLite: LiveData<Boolean> = _navigateToSQLite

    private val _navigateToRegistration = MutableLiveData(false)
    val navigateToRegistration: LiveData<Boolean> = _navigateToRegistration

    private val _navigateToCrypto = MutableLiveData(false)
    val navigateToCrypto: LiveData<Boolean> = _navigateToCrypto

    fun onSharedPreferencesClicked() {
        _navigateToSharedPreferences.value = true
    }

    fun onSQLiteClicked() {
        _navigateToSQLite.value = true
    }

    fun onRegistrationClicked() {
        _navigateToRegistration.value = true
    }

    fun onCryptoClicked() {
        _navigateToCrypto.value = true
    }

    fun onNavigatedToSharedPreferences() {
        _navigateToSharedPreferences.value = false
    }

    fun onNavigatedToSQLite() {
        _navigateToSQLite.value = false
    }

    fun onNavigatedToRegistration() {
        _navigateToRegistration.value = false
    }

    fun onNavigatedToCrypto() {
        _navigateToCrypto.value = false
    }
}
