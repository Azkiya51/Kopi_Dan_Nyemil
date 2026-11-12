package com.kopidannyemil.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.kopidannyemil.app.data.AppRepository
import com.kopidannyemil.app.data.toRupiah
import com.kopidannyemil.app.databinding.ActivityConfirmationBinding

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvGreeting.text = "Halo ${AppRepository.userName},"
        binding.tvSummary.text =
            "${AppRepository.lastOrderCount} item  •  ${AppRepository.lastOrderTotal.toRupiah()}"
        binding.tvAddress.text = "Dikirim ke: ${AppRepository.lastOrderAddress}"

        binding.checkContainer.apply {
            scaleX = 0f
            scaleY = 0f
            animate().scaleX(1f).scaleY(1f)
                .setDuration(700)
                .setInterpolator(OvershootInterpolator())
                .start()
        }

        binding.btnOk.setOnClickListener { backToHome() }
        onBackPressedDispatcher.addCallback(this) { backToHome() }
    }

    private fun backToHome() {
        startActivity(
            Intent(this, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        )
        finish()
    }
}
