package com.kopidannyemil.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kopidannyemil.app.data.DummyData
import com.kopidannyemil.app.data.User

/** State aplikasi (in-memory, tanpa database). */
class AppViewModel : ViewModel() {

    private val users = mutableStateListOf(User("Budi Santoso", "demo", "demo123"))

    var currentUser by mutableStateOf<User?>(null)
        private set

    /** id menu -> jumlah */
    val cart = mutableStateMapOf<Int, Int>()

    var lastOrderTotal by mutableStateOf(0)
        private set
    var lastOrderCount by mutableStateOf(0)
        private set
    var lastOrderAddress by mutableStateOf("")
        private set

    val userName: String get() = currentUser?.name ?: "Teman"

    val totalItems: Int get() = cart.values.sum()
    val totalPrice: Int
        get() = cart.entries.sumOf { (id, qty) ->
            (DummyData.menu.firstOrNull { it.id == id }?.price ?: 0) * qty
        }

    /** @return pesan error, atau null jika berhasil */
    fun login(username: String, password: String): String? {
        if (username.isBlank() || password.isBlank()) return "Username dan password wajib diisi"
        val user = users.firstOrNull { it.username == username.trim() && it.password == password }
            ?: return "Username atau password salah"
        currentUser = user
        return null
    }

    fun register(name: String, username: String, password: String): String? {
        if (name.isBlank() || username.isBlank() || password.isBlank()) return "Semua kolom wajib diisi"
        if (password.length < 6) return "Password minimal 6 karakter"
        if (users.any { it.username.equals(username.trim(), true) }) return "Username sudah dipakai"
        val user = User(name.trim(), username.trim(), password)
        users.add(user)
        currentUser = user
        return null
    }

    fun updateName(newName: String) {
        val user = currentUser ?: return
        val updated = user.copy(name = newName.trim())
        val idx = users.indexOfFirst { it.username == user.username }
        if (idx >= 0) users[idx] = updated
        currentUser = updated
    }

    fun add(id: Int) { cart[id] = (cart[id] ?: 0) + 1 }

    fun remove(id: Int) {
        val q = cart[id] ?: return
        if (q <= 1) cart.remove(id) else cart[id] = q - 1
    }

    fun checkout(address: String) {
        lastOrderTotal = totalPrice
        lastOrderCount = totalItems
        lastOrderAddress = address
        cart.clear()
    }

    fun logout() {
        currentUser = null
        cart.clear()
    }
}
