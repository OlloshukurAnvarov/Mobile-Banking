package com.leaf.mobilebanking.ui.fragment.signInFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.mobilebanking.R
import com.leaf.mobilebanking.databinding.FragmentSignInBinding
import com.redmadrobot.inputmask.MaskedTextChangedListener

class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private val binding: FragmentSignInBinding by viewBinding()

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

            val listener = MaskedTextChangedListener("[00] [000] [00] [00]", phoneNumber)
            phoneNumber.addTextChangedListener(listener)
            phoneNumber.onFocusChangeListener = listener

        }
    }

}