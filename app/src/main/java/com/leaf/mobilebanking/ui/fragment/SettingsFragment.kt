package com.leaf.mobilebanking.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.mobilebanking.R
import com.leaf.mobilebanking.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val binding: FragmentSettingsBinding by viewBinding()
    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            back.setOnClickListener {
                navController.popBackStack()
            }

            profileTextView.setOnClickListener {
                navController.navigate(R.id.action_settingsFragment_to_profileFragment)
            }

            securityTextView.setOnClickListener {  }

            phoneNumberTextView.setOnClickListener {  }
        }
    }

}