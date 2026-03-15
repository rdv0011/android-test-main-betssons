package com.betsson.interviewtest.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.betsson.interviewtest.R
import com.betsson.interviewtest.presentation.state.BetsUiState
import com.betsson.interviewtest.presentation.ui.adapter.BetListAdapter
import com.betsson.interviewtest.presentation.viewmodel.BetsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BetsFragment : Fragment() {

    private val viewModel: BetsViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingProgress: ProgressBar
    private lateinit var updateOddsButton: Button
    private lateinit var adapter: BetListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(view)
        observeViewModel()
        setupClickListeners()
    }

    private fun initializeViews(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        loadingProgress = view.findViewById(R.id.loading_progress)
        updateOddsButton = view.findViewById(R.id.update_odds_button)
        adapter = BetListAdapter()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    render(state)
                }
            }
        }
    }

    private fun render(state: BetsUiState) {
        when (state) {
            is BetsUiState.Loading -> setLoading(true)

            is BetsUiState.Success -> {
                setLoading(false)
                adapter.submitList(state.bets)
            }

            is BetsUiState.Error -> {
                setLoading(false)
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupClickListeners() {
        updateOddsButton.setOnClickListener {
            viewModel.updateOdds()
        }
    }

    private fun setLoading(isLoading: Boolean) {
        loadingProgress.visibility = if (isLoading) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}
