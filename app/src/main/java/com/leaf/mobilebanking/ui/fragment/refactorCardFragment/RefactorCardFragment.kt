package com.leaf.mobilebanking.ui.fragment.refactorCardFragment

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
import com.leaf.mobilebanking.databinding.FragmentRefactorCardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RefactorCardFragment : Fragment(R.layout.fragment_refactor_card) {
    private val binding: FragmentRefactorCardBinding by viewBinding()
    private val navController by lazy { findNavController() }
    private val viewModel: RefactorCardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.RESUMED) {

                launch {
                    viewModel.successFlow.collect {
                        navController.navigate(R.id.action_refactorCardFragment_to_successfulFragment)
                    }
                }

                launch {
                    viewModel.errorFlow.collect { error ->

                        binding.errorMessage.visibility = View.VISIBLE

                        binding.errorMessage.text = when (error) {
                            ErrorCodes.CARD_NAME_ERROR ->
                                getString(R.string.minimum_length_of_cardname_is_3)

                            ErrorCodes.CARD_DIGITS_ERROR ->
                                getString(R.string.invalid_card_number)

                            ErrorCodes.MONTH_YEAR_ERROR ->
                                getString(R.string.month_or_year_is_invalid)

                            else -> getString(R.string.something_wrong_please_try_again_later)
                        }

                    }
                }

                launch {
                    viewModel.errorIOFlow.collect { error ->

                        Snackbar.make(binding.submit.rootView, error, Snackbar.LENGTH_SHORT)
                            .setAnchorView(binding.submit)
                            .setAction("Action", null).show()

                    }
                }

                launch {
                    viewModel.noNetworkFlow.collect {
                        Snackbar.make(
                            binding.submit.rootView,
                            requireContext().getString(R.string.please_connect_to_internet),
                            Snackbar.LENGTH_SHORT
                        )
                            .setAnchorView(binding.submit)
                            .setAction("Action", null).show()
                    }
                }

            }
        }

        binding.apply {

        }

    }
}