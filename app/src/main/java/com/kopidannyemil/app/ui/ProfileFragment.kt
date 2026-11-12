package com.kopidannyemil.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.kopidannyemil.app.R
import com.kopidannyemil.app.data.AppRepository
import com.kopidannyemil.app.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        val username = AppRepository.currentUser?.username ?: "-"
        binding.etName.setText(AppRepository.userName)
        binding.etUsername.setText(username)
        binding.tvUsername.text = "@$username"
        bindHeader()

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            if (name.isBlank()) {
                Snackbar.make(binding.root, "Nama tidak boleh kosong", Snackbar.LENGTH_SHORT).show()
            } else {
                AppRepository.updateName(name)
                bindHeader()
                Snackbar.make(binding.root, "Profil berhasil disimpan", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.btnLogout.setOnClickListener {
            AppRepository.logout()
            startActivity(
                Intent(requireContext(), SignUpActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
        }
    }

    private fun bindHeader() {
        binding.tvGreeting.text = "Halo ${AppRepository.userName},"
        binding.tvAvatar.text = AppRepository.userName.take(1).uppercase()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
