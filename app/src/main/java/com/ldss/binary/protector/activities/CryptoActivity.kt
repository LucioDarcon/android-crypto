package com.ldss.binary.protector.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ldss.binary.protector.R
import com.ldss.binary.protector.databinding.ActivityCryptoBinding
import com.ldss.binary.protector.viewmodels.CryptoViewModel

class CryptoActivity : AppCompatActivity() {

    private val viewModel: CryptoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityCryptoBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_crypto,
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }
}
