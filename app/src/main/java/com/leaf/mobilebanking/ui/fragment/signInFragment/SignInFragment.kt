package com.leaf.mobilebanking.ui.fragment.signInFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.leaf.mobilebanking.R
import com.leaf.mobilebanking.data.constants.ErrorCodes
import com.leaf.mobilebanking.databinding.FragmentSignInBinding
import com.leaf.mobilebanking.extensions.formatter
import com.leaf.mobilebanking.extensions.toPhone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private val binding: FragmentSignInBinding by viewBinding()
    private val navController by lazy { findNavController() }
    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (viewModel.isAgreed())
            binding.cookiesLayout.visibility = View.GONE

        binding.apply {

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                continueButton.checking(isChecked)
            }

            continueButton.setOnClickListener {
                cookiesLayout.visibility = View.GONE
                viewModel.agreedCookies()
            }

            enterButton.setOnClickListener {

                errorMessage.visibility = View.GONE

                val phone = phoneNumber.text.toString().toPhone()
                val password = password.text.toString().trim()

                viewModel.signIn(password, phone)

            }

            signUpAsk.setOnClickListener {
                navController.navigate(R.id.action_signInFragment_to_signUpFragment)
            }

            formatter("[00] [000] [00] [00]", phoneNumber)

        }


        viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.RESUMED) {

                launch {
                    viewModel.openSecurityFlow.collect {

                        navController.navigate(R.id.action_signInFragment_to_securityFragment)

                    }
                }

                launch {
                    viewModel.errorFlow.collect { error ->

                        binding.errorMessage.visibility = View.VISIBLE

                        binding.errorMessage.text = when (error) {

                            ErrorCodes.PHONE_NUMBER_ERROR ->
                                getString(R.string.invalid_phone_number)

                            ErrorCodes.PASSWORD_ERROR ->
                                getString(R.string.minimum_length_of_password_is_4)

                            else -> getString(R.string.something_wrong_please_try_again_later)
                        }

                    }
                }

                launch {
                    viewModel.errorIOFlow.collect { error ->

                        Snackbar.make(binding.enterButton.rootView, error, Snackbar.LENGTH_SHORT)
                            .setAnchorView(binding.enterButton)
                            .setAction("Action", null).show()

                    }
                }

                launch {
                    viewModel.noNetworkFlow.collect {
                        Snackbar.make(
                            binding.enterButton.rootView,
                            requireContext().getString(R.string.please_connect_to_internet),
                            Snackbar.LENGTH_SHORT
                        )
                            .setAnchorView(binding.enterButton)
                            .setAction("Action", null).show()
                    }
                }

            }
        }

    }
}