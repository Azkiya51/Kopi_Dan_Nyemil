package com.kopidannyemil.app.data

import com.kopidannyemil.app.R

data class MenuItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val category: String,
    val emoji: String,
    val image: Any
)

data class User(
    val name: String,
    val username: String,
    val password: String
)

object DummyData {
    val categories = listOf("Semua", "Minuman", "Makanan")

    val menu = listOf(
        MenuItem(
            1, "Kopi Susu Gula Aren", "Espresso, susu, gula aren",
            22000, "Minuman", "☕", R.drawable.kopi_susu_gula_aren
        ),
        MenuItem(
            2, "Donat Cokelat", "Donat empuk, glaze cokelat",
            12000, "Makanan", "🍩", R.drawable.donat_cokelat
        ),
        MenuItem(
            3, "Sourdough Loaf", "Roti fermentasi alami",
            35000, "Makanan", "🍞", R.drawable.sourdough_loaf
        ),
        MenuItem(
            4, "Cappuccino", "Espresso, foam susu lembut",
            25000, "Minuman", "🥛", R.drawable.cappuccino
        ),
        MenuItem(
            5, "Matcha Latte", "Matcha Jepang, susu segar",
            27000, "Minuman", "🍵", R.drawable.matcha_latte
        ),
        MenuItem(
            6, "Croissant Butter", "Renyah berlapis, butter premium",
            20000, "Makanan", "🥐", R.drawable.croissant_butter
        ),
        MenuItem(
            7, "Es Cokelat", "Cokelat pekat, susu dingin",
            24000, "Minuman", "🍫", R.drawable.es_cokelat
        )
    )
}

fun Int.toRupiah(): String =
    "Rp " + this.toString().reversed().chunked(3).joinToString(".").reversed()