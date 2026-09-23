package com.ldss.binary.protector.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ldss.binary.protector.R
import com.ldss.binary.protector.databinding.ActivitySqliteBinding
import com.ldss.binary.protector.viewmodels.SQLiteViewModel

class SQLiteActivity : AppCompatActivity() {

    private val viewModel: SQLiteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySqliteBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_sqlite,
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }
}
