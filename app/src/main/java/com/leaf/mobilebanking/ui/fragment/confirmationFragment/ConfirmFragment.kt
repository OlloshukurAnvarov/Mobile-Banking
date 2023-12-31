package com.leaf.mobilebanking.ui.fragment.confirmationFragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
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
import com.leaf.mobilebanking.databinding.FragmentConfirmBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConfirmFragment : Fragment(R.layout.fragment_confirm) {
    private val binding: FragmentConfirmBinding by viewBinding()
    private val navController by lazy { findNavController() }
    private val viewModel: ConfirmViewModel by viewModels()
    private lateinit var timer: CountDownTimer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.sendSMS(requireContext())

        binding.apply {

            confirm.setOnClickListener {
                if (!errorMessage.isEnabled)
                    checking(0)
                val code = verification.text.toString()
                viewModel.verify(code)
            }

            back.setOnClickListener {
                timer.cancel()
                navController.popBackStack()
            }

        }

        timer = object : CountDownTimer(60_000, 1_000) {
            override fun onTick(p0: Long) {
                updateCountdownText(p0)
            }

            override fun onFinish() {
                checking(2)
                binding.confirm.checking(false)
            }

        }.start()

        viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.RESUMED) {

                launch {
                    viewModel.successMessageFlow.collect {

                        navController.navigate(
                            R.id.action_confirmFragment_to_successfulFragment, bundleOf(
                                "transfer-index" to true,
                                "info-index" to getString(R.string.successfully_transferred)
                            )
                        )

                    }
                }

                launch {
                    viewModel.errorFlow.collect { error ->

                        checking(1)

                        binding.errorMessage.text = when (error) {
                            ErrorCodes.CODE_ERROR ->
                                getString(R.string.incorrect_code)

                            else -> getString(R.string.something_wrong_please_try_again_later)
                        }

                    }
                }

                launch {
                    viewModel.errorIOFlow.collect { error ->

                        if (error.length > 40)
                            navController.popBackStack()
                        else
                            Snackbar.make(binding.confirm.rootView, error, Snackbar.LENGTH_SHORT)
                                .setAnchorView(binding.confirm)
                                .setAction("Action", null).show()

                    }
                }

                launch {
                    viewModel.noNetworkFlow.collect {
                        Snackbar.make(
                            binding.confirm.rootView,
                            requireContext().getString(R.string.please_connect_to_internet),
                            Snackbar.LENGTH_SHORT
                        )
                            .setAnchorView(binding.confirm)
                            .setAction("Action", null).show()
                    }
                }

            }
        }

    }

    private fun checking(n: Int) {
        binding.apply {
            errorMessage.setTextColor(
                when (n) {
                    0 -> {
                        chronometer.visibility = View.VISIBLE
                        errorMessage.isEnabled = false
                        errorMessage.text = getString(R.string.request_via)
                        requireContext().getColor(R.color.black)
                    }

                    1 -> {
                        chronometer.visibility = View.GONE
                        errorMessage.isEnabled = false
                        requireContext().getColor(R.color.error_red)
                    }

                    else -> {
                        errorMessage.isEnabled = true
                        errorMessage.setOnClickListener {
                            navController.popBackStack()
                            timer.start()
                            checking(0)
                            confirm.checking(true)
                        }
                        chronometer.visibility = View.GONE

                        errorMessage.text = getString(R.string.send_again)

                        requireContext().getColor(R.color.primary_blue)
                    }
                }
            )
        }
    }

    private fun updateCountdownText(millisUntilFinished: Long) {
        val minutes = millisUntilFinished / 60000
        val seconds = (millisUntilFinished % 60000) / 1000
        val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
        binding.chronometer.text = timeLeftFormatted
    }

    override fun onDestroyView() {
        timer.cancel()
        viewModel.stopService(requireContext())
        super.onDestroyView()
    }
}