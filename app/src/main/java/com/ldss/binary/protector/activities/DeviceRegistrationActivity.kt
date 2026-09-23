package com.ldss.binary.protector.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ldss.binary.protector.R
import com.ldss.binary.protector.databinding.ActivityDeviceRegistrationBinding
import com.ldss.binary.protector.viewmodels.DeviceRegistrationViewModel

class DeviceRegistrationActivity : AppCompatActivity() {

    private val viewModel: DeviceRegistrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityDeviceRegistrationBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_device_registration,
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkRegistrationStatus()
    }
}
