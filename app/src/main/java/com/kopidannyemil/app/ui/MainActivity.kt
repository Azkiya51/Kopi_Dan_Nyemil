package com.kopidannyemil.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.kopidannyemil.app.R
import com.kopidannyemil.app.data.AppRepository
import com.kopidannyemil.app.data.toRupiah
import com.kopidannyemil.app.databinding.ActivityMainBinding

/** Host untuk Home / Order (Alamat) / Profile dengan Bottom Navigation. */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentTab = R.id.nav_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Jika proses aplikasi di-restart dan user hilang, kembali ke awal
        if (AppRepository.currentUser == null) {
            startActivity(
                Intent(this, SplashActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
            finish()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener { item ->
            showTab(item.itemId)
            true
        }
        binding.cartBar.setOnClickListener {
            binding.bottomNav.selectedItemId = R.id.nav_order
        }

        if (savedInstanceState == null) showTab(R.id.nav_home) else updateChrome()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Dipanggil dari halaman konfirmasi (tombol "Oke") -> kembali ke Home
        binding.bottomNav.menu.findItem(R.id.nav_home).isChecked = true
        showTab(R.id.nav_home)
    }

    private fun showTab(itemId: Int) {
        currentTab = itemId
        val fragment: Fragment = when (itemId) {
            R.id.nav_order -> OrderFragment()
            R.id.nav_profile -> ProfileFragment()
            else -> HomeFragment()
        }
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.container, fragment)
            .commit()
        updateChrome()
    }

    /** Dipanggil fragment setiap kali isi keranjang berubah. */
    fun refreshCart() = updateChrome()

    @Suppress("DEPRECATION")
    private fun updateChrome() {
        val count = AppRepository.totalItems

        // Badge pada tab Order
        val badge = binding.bottomNav.getOrCreateBadge(R.id.nav_order)
        badge.backgroundColor = ContextCompat.getColor(this, R.color.blue_600)
        badge.badgeTextColor = ContextCompat.getColor(this, R.color.white)
        if (count > 0) {
            badge.number = count
            badge.isVisible = true
        } else {
            badge.isVisible = false
        }

        // Bar ringkasan keranjang (hanya di Home)
        val onHome = currentTab == R.id.nav_home
        binding.cartBar.visibility = if (count > 0 && onHome) View.VISIBLE else View.GONE
        binding.tvCartCount.text = "$count item"
        binding.tvCartTotal.text = "${AppRepository.totalPrice.toRupiah()}  •  Lihat pesanan"

        // Warna status bar: biru di Home, biru muda di halaman lain
        window.statusBarColor = ContextCompat.getColor(
            this, if (onHome) R.color.blue_500 else R.color.blue_100
        )
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = !onHome
    }
}
