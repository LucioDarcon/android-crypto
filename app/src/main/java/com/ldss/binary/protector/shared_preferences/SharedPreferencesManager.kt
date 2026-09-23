package com.ldss.binary.protector.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.ldss.binary.protector.crypto.CryptoLocal
import com.ldss.binary.protector.utils.Constants

class SharedPreferencesManager(private val context: Context) {
    private var prefs: SharedPreferences? = null

    private val mCryptoLocal = CryptoLocal()

    fun initialize(userName: String) {
        prefs = context.getSharedPreferences(Constants.getKeyLocal(userName), Context.MODE_PRIVATE)
    }

    fun save(key: String, value: String) {
        prefs?.edit {
            putString(key, value)
        }
    }

    fun getValue(key: String): String? {
        return prefs?.getString(key, null)
    }

    fun remove(key: String) {
        prefs?.edit {
            remove(key)
        }
    }

    fun isInitialized(): Boolean = prefs != null
}
