package com.kopidannyemil.app.ui

import android.content.Context
import android.content.Intent

/** Buka halaman utama dan hapus semua halaman sebelumnya (splash/login/dll). */
fun Context.openHome() {
    startActivity(
        Intent(this, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    )
}
