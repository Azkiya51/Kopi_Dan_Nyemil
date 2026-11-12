package com.kopidannyemil.app.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.kopidannyemil.app.data.AppRepository
import com.kopidannyemil.app.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        val hideError = { _: CharSequence? -> binding.tvError.visibility = View.GONE }
        binding.etName.doAfterTextChanged { hideError(it) }
        binding.etUsername.doAfterTextChanged { hideError(it) }
        binding.etPassword.doAfterTextChanged { hideError(it) }

        binding.btnRegister.setOnClickListener {
            val error = AppRepository.register(
                binding.etName.text.toString(),
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString()
            )
            if (error == null) {
                openHome()
            } else {
                binding.tvError.text = error
                binding.tvError.visibility = View.VISIBLE
            }
        }
    }
}
