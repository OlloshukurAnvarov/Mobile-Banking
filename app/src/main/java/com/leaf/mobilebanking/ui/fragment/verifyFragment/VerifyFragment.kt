package com.leaf.mobilebanking.ui.fragment.verifyFragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.mobilebanking.R
import com.leaf.mobilebanking.databinding.FragmentVerifyBinding

class VerifyFragment : Fragment(R.layout.fragment_verify) {
    private val binding: FragmentVerifyBinding by viewBinding()
    private val navController by lazy { findNavController() }
    private val iterator by lazy { binding.verification.list.iterator() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }
}