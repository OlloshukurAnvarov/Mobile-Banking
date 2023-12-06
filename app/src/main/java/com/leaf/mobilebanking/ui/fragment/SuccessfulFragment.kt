package com.leaf.mobilebanking.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.mobilebanking.R
import com.leaf.mobilebanking.databinding.FragmentSuccessfulBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessfulFragment : Fragment(R.layout.fragment_successful) {
    private val binding: FragmentSuccessfulBinding by viewBinding()
    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val text = arguments?.getString("info-index", null)
        val isTransfers = arguments?.getBoolean("transfer-index", false) ?: false

        binding.receipt.visibility = if (isTransfers) View.VISIBLE else View.GONE

        if (text != null)
            binding.infoText.text = text

        binding.close.setOnClickListener {
            navController.popBackStack()
        }

    }

}