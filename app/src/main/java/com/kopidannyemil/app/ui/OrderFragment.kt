package com.kopidannyemil.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.kopidannyemil.app.R
import com.kopidannyemil.app.data.AppRepository
import com.kopidannyemil.app.data.DummyData
import com.kopidannyemil.app.data.toRupiah
import com.kopidannyemil.app.databinding.FragmentOrderBinding
import com.kopidannyemil.app.databinding.ItemOrderRowBinding

/** Halaman Alamat pengiriman (Order). */
class OrderFragment : Fragment(R.layout.fragment_order) {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderBinding.bind(view)

        binding.etName.setText(AppRepository.userName)
        showSummary()

        binding.etName.doAfterTextChanged { binding.tvError.visibility = View.GONE }
        binding.etAddress.doAfterTextChanged { binding.tvError.visibility = View.GONE }

        binding.btnOrder.setOnClickListener { submitOrder() }
    }

    private fun showSummary() {
        binding.llItems.removeAllViews()
        if (AppRepository.cart.isEmpty()) {
            binding.tvEmpty.visibility = View.VISIBLE
            binding.llItems.visibility = View.GONE
            binding.divider.visibility = View.GONE
            binding.totalRow.visibility = View.GONE
            return
        }
        binding.tvEmpty.visibility = View.GONE
        binding.llItems.visibility = View.VISIBLE
        binding.divider.visibility = View.VISIBLE
        binding.totalRow.visibility = View.VISIBLE

        for ((id, qty) in AppRepository.cart.entries.sortedBy { it.key }) {
            val menu = DummyData.menu.first { it.id == id }
            val row = ItemOrderRowBinding.inflate(layoutInflater, binding.llItems, false)
            row.tvRowName.text = "${menu.name} × $qty"
            row.tvRowPrice.text = (menu.price * qty).toRupiah()
            binding.llItems.addView(row.root)
        }
        binding.tvTotal.text = AppRepository.totalPrice.toRupiah()
    }

    private fun submitOrder() {
        val name = binding.etName.text.toString()
        val address = binding.etAddress.text.toString()
        val landmark = binding.etLandmark.text.toString()

        val error = when {
            AppRepository.cart.isEmpty() -> "Keranjang masih kosong"
            name.isBlank() || address.isBlank() -> "Nama dan alamat wajib diisi"
            else -> null
        }
        if (error != null) {
            binding.tvError.text = error
            binding.tvError.visibility = View.VISIBLE
            return
        }

        val fullAddress = address.trim() +
            if (landmark.isNotBlank()) " (${landmark.trim()})" else ""
        AppRepository.checkout(fullAddress)
        (requireActivity() as MainActivity).refreshCart()
        startActivity(Intent(requireContext(), ConfirmationActivity::class.java))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
