package com.ldss.binary.protector.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ldss.binary.protector.R
import com.ldss.binary.protector.databinding.ActivityLoginBinding
import com.ldss.binary.protector.viewmodels.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_login,
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.navigateToMain.observe(this) { username ->
            username?.let {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("username", it)
                startActivity(intent)
                finish()
                viewModel.onNavigatedToMain()
            }
        }
    }
}
