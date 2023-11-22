package com.leaf.mobilebanking.ui.fragment.signUpFragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.leaf.mobilebanking.R
import com.leaf.mobilebanking.data.api.AuthAPI
import com.leaf.mobilebanking.data.constants.ErrorCodes
import com.leaf.mobilebanking.databinding.FragmentSignUpBinding
import com.leaf.mobilebanking.extensions.formatter
import com.leaf.mobilebanking.extensions.toPhone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private val binding: FragmentSignUpBinding by viewBinding()
    private val navController by lazy { findNavController() }
    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {

            viewLifecycleOwner.lifecycleScope.launch {

                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.openVerifyFlow.collect {

                        Snackbar.make(signUp.rootView, "Verify ochiladi", Snackbar.LENGTH_SHORT)
                            .setAnchorView(signUp)
                            .setAction("Action", null).show()

                    }
                }

                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.errorFlow.collect { error ->

                        errorMessage.visibility = View.VISIBLE

                        errorMessage.text = when (error) {
                            ErrorCodes.FIRST_NAME_ERROR ->
                                requireContext().getString(R.string.minimum_length_of_firstname_is_3)

                            ErrorCodes.LAST_NAME_ERROR ->
                                requireContext().getString(R.string.minimum_length_of_lastname_is_3)

                            ErrorCodes.PHONE_NUMBER_ERROR ->
                                "requireContext().getString(R.string.)"

                            ErrorCodes.PASSWORD_ERROR ->
                                requireContext().getString(R.string.minimum_length_of_password_is_4)

                            else -> requireContext().getString(R.string.something_wrong_please_try_again_later)
                        }

                    }
                }

                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.noNetworkFlow.collect {
                        Snackbar.make(
                            signUp.rootView,
                            requireContext().getString(R.string.please_connect_to_internet),
                            Snackbar.LENGTH_SHORT
                        )
                            .setAnchorView(signUp)
                            .setAction("Action", null).show()
                    }
                }

            }

            signInAsk.setOnClickListener {
                navController.popBackStack()
            }

            signUp.setOnClickListener {
                val firstName = firstName.text.toString()
                val lastName = lastName.text.toString()
                val phone = phoneNumber.text.toString().toPhone()
                val password = password.text.toString()

                viewModel.signUp(firstName, lastName, password, phone)

            }

            formatter("[00] [000] [00] [00]", phoneNumber)

        }

    }

}


//private fun vibrateMe() {
//    val vibrator = getSystemService(AppCompatActivity.VIBRATOR_SERVICE) as Vibrator
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        vibrator.vibrate(VibrationEffect.createOneShot(75, VibrationEffect.DEFAULT_AMPLITUDE))
//    } else {
//        //deprecated in API 26
//        vibrator.vibrate(75)
//    }
//}