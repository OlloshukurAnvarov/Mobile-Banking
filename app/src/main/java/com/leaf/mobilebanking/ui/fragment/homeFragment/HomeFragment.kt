package com.leaf.mobilebanking.ui.fragment.homeFragment

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
import com.leaf.mobilebanking.databinding.FragmentHomeBinding
import com.leaf.mobilebanking.domain.entity.CardData
import com.leaf.mobilebanking.ui.adapter.CardAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()
    private val navController by lazy { findNavController() }
    private val viewModel: HomeViewModel by viewModels()
    private val data = ArrayList<CardData>()
    private val adapter by lazy { CardAdapter(data) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recycler.adapter = adapter

        binding.emptyList.setOnClickListener {
            viewModel.cards()
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