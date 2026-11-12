package com.kopidannyemil.app.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.kopidannyemil.app.data.AppRepository
import com.kopidannyemil.app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        binding.etUsername.doAfterTextChanged { binding.tvError.visibility = View.GONE }
        binding.etPassword.doAfterTextChanged { binding.tvError.visibility = View.GONE }

        binding.btnLogin.setOnClickListener {
            val error = AppRepository.login(
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
