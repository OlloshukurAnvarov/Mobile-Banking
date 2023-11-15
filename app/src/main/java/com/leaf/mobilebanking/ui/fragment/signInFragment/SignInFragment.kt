package com.leaf.mobilebanking.ui.fragment.signInFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.mobilebanking.R
import com.leaf.mobilebanking.databinding.FragmentSignInBinding
import com.leaf.mobilebanking.extensions.formatter
import com.redmadrobot.inputmask.MaskedTextChangedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private val binding: FragmentSignInBinding by viewBinding()
    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                continueButton.checking(isChecked)
            }

            continueButton.setOnClickListener {
                if (checkbox.isChecked)
                    cookiesLayout.visibility = View.GONE
                else
                    cookiesLayout.visibility = View.VISIBLE
            }

            signUpAsk.setOnClickListener {
                navController.navigate(R.id.action_signInFragment_to_signUpFragment)
            }

            formatter("[00] [000] [00] [00]", phoneNumber)

        }
    }

}