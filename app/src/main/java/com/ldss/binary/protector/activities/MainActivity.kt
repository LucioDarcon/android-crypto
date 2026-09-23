package com.ldss.binary.protector.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ldss.binary.protector.R
import com.ldss.binary.protector.databinding.ActivityMainBinding
import com.ldss.binary.protector.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private var mUsername: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkExtras()

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main,
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.navigateToSharedPreferences.observe(this) { navigate ->
            if (navigate) {
                startActivity(Intent(this, SharedPreferencesActivity::class.java))
                viewModel.onNavigatedToSharedPreferences()
            }
        }

        viewModel.navigateToSQLite.observe(this) { navigate ->
            if (navigate) {
                startActivity(Intent(this, SQLiteActivity::class.java))
                viewModel.onNavigatedToSQLite()
            }
        }

        viewModel.navigateToRegistration.observe(this) { navigate ->
            if (navigate) {
                startActivity(Intent(this, DeviceRegistrationActivity::class.java))
                viewModel.onNavigatedToRegistration()
            }
        }

        viewModel.navigateToCrypto.observe(this) { navigate ->
            if (navigate) {
                startActivity(Intent(this, CryptoActivity::class.java))
                viewModel.onNavigatedToCrypto()
            }
        }
    }

    private fun checkExtras() {
        mUsername = intent.getStringExtra("username")
        if (mUsername.isNullOrEmpty()) {
            throw Exception("Username is empty")
        }
    }

    companion object {
        init {
            System.loadLibrary("protector")
        }
    }
}
