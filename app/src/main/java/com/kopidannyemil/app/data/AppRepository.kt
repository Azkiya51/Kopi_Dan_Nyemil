package com.kopidannyemil.app.data

/** Penyimpanan data in-memory (tanpa database). */
object AppRepository {

    private val users = mutableListOf(User("Budi Santoso", "demo", "demo123"))

    var currentUser: User? = null
        private set

    /** id menu -> jumlah */
    val cart = LinkedHashMap<Int, Int>()

    var lastOrderTotal = 0
        private set
    var lastOrderCount = 0
        private set
    var lastOrderAddress = ""
        private set

    val userName: String get() = currentUser?.name ?: "Teman"

    val totalItems: Int get() = cart.values.sum()

    val totalPrice: Int
        get() {
            var total = 0
            for ((id, qty) in cart) {
                val price = DummyData.menu.firstOrNull { it.id == id }?.price ?: 0
                total += price * qty
            }
            return total
        }

    /** @return pesan error, atau null jika berhasil */
    fun login(username: String, password: String): String? {
        if (username.isBlank() || password.isBlank()) return "Username dan password wajib diisi"
        val user = users.firstOrNull { it.username == username.trim() && it.password == password }
            ?: return "Username atau password salah"
        currentUser = user
        return null
    }

    /** @return pesan error, atau null jika berhasil */
    fun register(name: String, username: String, password: String): String? {
        if (name.isBlank() || username.isBlank() || password.isBlank()) return "Semua kolom wajib diisi"
        if (password.length < 6) return "Password minimal 6 karakter"
        if (users.any { it.username.equals(username.trim(), ignoreCase = true) }) return "Username sudah dipakai"
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

    fun quantityOf(id: Int): Int = cart[id] ?: 0

    fun add(id: Int) {
        cart[id] = quantityOf(id) + 1
    }

    fun remove(id: Int) {
        val q = quantityOf(id)
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
