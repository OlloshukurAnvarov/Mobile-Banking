package com.leaf.mobilebanking.ui.fragment.securityFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.mobilebanking.R
import com.leaf.mobilebanking.databinding.FragmentSecurityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SecurityFragment : Fragment(R.layout.fragment_security) {
    private val binding: FragmentSecurityBinding by viewBinding()
    private val navController by lazy { findNavController() }
    private val viewModel: SecurityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.pinCode.setViewModel(viewModel)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.password.collect { password ->
                    binding.pinCode.setPinCode(password)
                }
            }
        }

        if (!viewModel.isLogged())
            binding.pinCode.madeSafe()
        else
            viewModel.makeReadyPass()
    }

}