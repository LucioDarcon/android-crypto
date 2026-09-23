package com.ldss.binary.protector.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ldss.binary.protector.R
import com.ldss.binary.protector.databinding.ActivitySharedPreferencesBinding
import com.ldss.binary.protector.viewmodels.SharedPreferencesViewModel

class SharedPreferencesActivity : AppCompatActivity() {

    private val viewModel: SharedPreferencesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySharedPreferencesBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_shared_preferences,
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }
}
