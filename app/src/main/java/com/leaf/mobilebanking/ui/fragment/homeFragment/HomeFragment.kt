package com.leaf.mobilebanking.ui.fragment.homeFragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.leaf.mobilebanking.R
import com.leaf.mobilebanking.databinding.FragmentHomeBinding
import com.leaf.mobilebanking.domain.entity.CardData
import com.leaf.mobilebanking.extensions.toPhoneSpace
import com.leaf.mobilebanking.ui.adapter.CardAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()
    private val navController by lazy { findNavController() }
    private val viewModel: HomeViewModel by viewModels()
    private val data = ArrayList<CardData>()
    private val adapter by lazy { CardAdapter(data) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {

            recycler.adapter = adapter

            adapter.setOnClickListener {
                navController.navigate(
                    R.id.action_homeFragment_to_refactorCardFragment,
                    bundleOf(
                        "selected-card-id" to data[it].id,
                        "selected-card-name" to data[it].name,
                        "selected-card-expires-date" to (data[it].expireMonth.toString() + "/" + data[it].expireYear.toString()
                            .takeLast(2)),
                        "selected-card-number" to data[it].pan.toPhoneSpace()
                    )
                )
            }

            emptyList.setOnClickListener {
                viewModel.cards()
            }

            addCard.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_refactorCardFragment)
            }

            send.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_transferFragment)
            }

            settings.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_settingsFragment)
            }

            val bottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet)

            // Set peek height (optional)
            bottomSheetBehavior.peekHeight = 200

            // Set up button click listener to show/hide bottom sheet
            dragHandle.setOnClickListener {
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                } else {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.RESUMED) {

                launch {
                    viewModel.cardsFlow.collect { cards ->
                        if (cards.isEmpty())
                            isEmpty(true)
                        else
                            isEmpty(false)
                        data.clear()
                        data.addAll(cards)
                        adapter.notifyDataSetChanged()

                    }
                }

                launch {
                    viewModel.errorFlow.collect {
                        isEmpty(true)
                    }
                }


                launch {
                    viewModel.errorIOFlow.collect { _ ->
                        isEmpty(true)
                    }
                }

                launch {
                    viewModel.noNetworkFlow.collect {
                        Snackbar.make(
                            binding.more.rootView,
                            requireContext().getString(R.string.please_connect_to_internet),
                            Snackbar.LENGTH_SHORT
                        )
                            .setAnchorView(binding.more)
                            .setAction("Action", null).show()
                    }
                }

            }
        }

    }

    private fun isEmpty(value: Boolean) {
        binding.apply {
            if (value) {
                recycler.visibility = View.GONE
                emptyList.visibility = View.VISIBLE
                progress.visibility = View.GONE
                addCardLayout.visibility = View.VISIBLE

                emptyList.setOnClickListener {
                    navController.navigate(R.id.action_homeFragment_to_refactorCardFragment)
                }

            } else {
                viewModel.delaying()
                recycler.visibility = View.VISIBLE
                emptyList.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        viewModel.cards()
        super.onResume()
    }

}