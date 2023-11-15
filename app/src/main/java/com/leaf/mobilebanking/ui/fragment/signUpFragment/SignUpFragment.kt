package com.leaf.mobilebanking.ui.fragment.signUpFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.mobilebanking.R
import com.leaf.mobilebanking.databinding.FragmentSignUpBinding
import com.leaf.mobilebanking.extensions.formatter
import com.redmadrobot.inputmask.MaskedTextChangedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private val binding: FragmentSignUpBinding by viewBinding()
    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {
            signInAsk.setOnClickListener {
                navController.popBackStack()
            }

            formatter("[00] [000] [00] [00]", phoneNumber)
            formatter("[00]/[00]/[0000]", dateOfBirth)

        }

    }

}