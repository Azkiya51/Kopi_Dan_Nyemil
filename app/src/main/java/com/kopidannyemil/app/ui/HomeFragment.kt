package com.kopidannyemil.app.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopidannyemil.app.R
import com.kopidannyemil.app.data.AppRepository
import com.kopidannyemil.app.data.DummyData
import com.kopidannyemil.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.tvGreeting.text = "Halo ${AppRepository.userName},"

        val adapter = MenuAdapter(DummyData.menu) {
            (requireActivity() as MainActivity).refreshCart()
        }
        binding.rvMenu.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMenu.adapter = adapter

        binding.chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            val category = when (checkedIds.firstOrNull()) {
                R.id.chipDrink -> "Minuman"
                R.id.chipFood -> "Makanan"
                else -> "Semua"
            }
            adapter.submit(
                if (category == "Semua") DummyData.menu
                else DummyData.menu.filter { it.category == category }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
