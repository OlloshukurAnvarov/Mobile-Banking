package com.leaf.mobilebanking.ui.fragment.signUpFragment

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
import com.leaf.mobilebanking.databinding.FragmentSignUpBinding
import com.leaf.mobilebanking.extensions.formatter
import com.leaf.mobilebanking.extensions.toPhone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private val binding: FragmentSignUpBinding by viewBinding()
    private val navController by lazy { findNavController() }
    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {

            signUp.setOnClickListener {

                errorMessage.visibility = View.GONE

                val firstName = firstName.text.toString().trim()
                val lastName = lastName.text.toString().trim()
                val phone = phoneNumber.text.toString().toPhone()
                val password = password.text.toString().trim()

                viewModel.signUp(firstName, lastName, password, phone)

            }

            signInAsk.setOnClickListener {
                navController.navigate(R.id.signInFragment)
            }

            formatter("[00] [000] [00] [00]", phoneNumber)

        }

        viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.RESUMED) {

                launch {
                    viewModel.openVerifyFlow.collect {

                        navController.navigate(R.id.action_signUpFragment_to_verifyFragment)

                    }
                }

                launch {
                    viewModel.errorFlow.collect { error ->

                        binding.errorMessage.visibility = View.VISIBLE

                        binding.errorMessage.text = when (error) {
                            ErrorCodes.FIRST_NAME_ERROR ->
                                getString(R.string.minimum_length_of_firstname_is_3)

                            ErrorCodes.LAST_NAME_ERROR ->
                                getString(R.string.minimum_length_of_lastname_is_3)

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

                        Snackbar.make(binding.signUp.rootView, error, Snackbar.LENGTH_SHORT)
                            .setAnchorView(binding.signUp)
                            .setAction("Action", null).show()

                    }
                }

                launch {
                    viewModel.noNetworkFlow.collect {
                        Snackbar.make(
                            binding.signUp.rootView,
                            requireContext().getString(R.string.please_connect_to_internet),
                            Snackbar.LENGTH_SHORT
                        )
                            .setAnchorView(binding.signUp)
                            .setAction("Action", null).show()
                    }
                }

            }
        }
    }
}