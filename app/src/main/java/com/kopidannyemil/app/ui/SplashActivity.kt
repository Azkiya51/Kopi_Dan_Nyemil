package com.kopidannyemil.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.kopidannyemil.app.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Animasi logo
        binding.logoContainer.apply {
            scaleX = 0.5f
            scaleY = 0.5f
            alpha = 0f
            animate()
                .scaleX(1f).scaleY(1f).alpha(1f)
                .setDuration(800)
                .setInterpolator(OvershootInterpolator())
                .start()
        }
        listOf(binding.tvTitle, binding.tvTagline).forEach {
            it.alpha = 0f
            it.animate().alpha(1f).setStartDelay(400).setDuration(700).start()
        }

        binding.btnStart.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }
}
