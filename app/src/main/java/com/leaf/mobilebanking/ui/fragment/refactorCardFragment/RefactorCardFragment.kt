package com.leaf.mobilebanking.ui.fragment.refactorCardFragment

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.leaf.mobilebanking.R
import com.leaf.mobilebanking.data.constants.ErrorCodes
import com.leaf.mobilebanking.databinding.FragmentRefactorCardBinding
import com.leaf.mobilebanking.extensions.formatter
import com.leaf.mobilebanking.extensions.toMonth
import com.leaf.mobilebanking.extensions.toPan
import com.leaf.mobilebanking.extensions.toYear
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RefactorCardFragment : Fragment(R.layout.fragment_refactor_card) {
    private val binding: FragmentRefactorCardBinding by viewBinding()
    private val navController by lazy { findNavController() }
    private val viewModel: RefactorCardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val _id = arguments?.getInt("selected-card-id")
        val _name = arguments?.getString("selected-card-name", null)
        val _date = arguments?.getString("selected-card-expires-date", null)
        val _pan = arguments?.getString("selected-card-number", null)

        if (_id != null && _name != null && _date != null && _pan != null)
            binding.apply {

                title.text = getString(R.string.edit_card)

                delete.visibility = View.VISIBLE

                cardNameEdit.apply {
                    setText(_name)
                    imeOptions = EditorInfo.IME_ACTION_DONE
                }

                cardDataEdit.apply {
                    setText(_date)
                    isEnabled = false
                }
                cardNumberEdit.apply {
                    setText(_pan)
                    isEnabled = false
                }

                submit.view {
                    it.text = getString(R.string.edit)
                }

                cardValidDate.text = _date
                cardPan.text = _pan
                cardName.text = _name

                cardNameEdit.addTextChangedListener {
                    cardName.text = it?.toString()
                }

                submit.setOnClickListener {
                    val n = cardNameEdit.text.toString()
                    cardName.text = n
                    if (_name == n)
                        navController.popBackStack()
                    else
                        viewModel.update(_id, n)
                }

                delete.setOnClickListener {
                    MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_App_MaterialAlertDialog)
                        .setTitle(getString(R.string.confirm_deletion))
                        .setMessage(getString(R.string.are_you_sure))
                        .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                            dialog.cancel()
                        }
                        .setPositiveButton(getString(R.string.delete)) { dialog, _ ->
                            viewModel.delete(_id)
                            dialog.dismiss()
                        }
                        .show()
                }

            }
        else
            binding.apply {

                cardNameEdit.addTextChangedListener {
                    cardName.text = it?.toString()
                }

                cardDataEdit.addTextChangedListener {
                    cardValidDate.text = it?.toString()
                }

                cardNumberEdit.addTextChangedListener {
                    cardPan.text = it?.toString()
                }

                submit.setOnClickListener {

                    errorMessage.visibility = View.GONE

                    val name = cardNameEdit.text.toString()
                    val month = cardValidDate.text.toString().toMonth()
                    val year = cardValidDate.text.toString().toYear()
                    val pan = cardNumberEdit.text.toString().toPan()

                    viewModel.addCard(month, year, name, pan)
                }

                formatter("[00]/[00]", cardDataEdit)
                formatter("[0000] [0000] [0000] [0000]", cardNumberEdit)

            }

        binding.back.setOnClickListener {
            navController.popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.RESUMED) {

                launch {
                    viewModel.successFlow.collect {
                        navController.navigate(R.id.action_refactorCardFragment_to_successfulFragment)
                    }
                }

                launch {
                    viewModel.successUpdateFlow.collect {
                        navController.navigate(
                            R.id.action_refactorCardFragment_to_successfulFragment,
                            bundleOf("info-index" to getString(R.string.successfully_edited))
                        )
                    }
                }

                launch {
                    viewModel.successDeletedFlow.collect {
                        navController.navigate(
                            R.id.action_refactorCardFragment_to_successfulFragment,
                            bundleOf("info-index" to getString(R.string.successfully_deleted))
                        )
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

    }
}