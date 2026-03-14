package com.betsson.interviewtest.presentation.ui.controller

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.betsson.interviewtest.R
import com.betsson.interviewtest.presentation.state.BetsUiState
import com.betsson.interviewtest.presentation.ui.adapter.ItemAdapter
import com.betsson.interviewtest.presentation.viewmodel.BetsViewModel
import kotlinx.coroutines.launch

class BetsUIController(
    private val activity: AppCompatActivity,
    private val viewModel: BetsViewModel
) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingProgress: ProgressBar
    private lateinit var adapter: ItemAdapter

    fun initialize() {
        initializeViews()
        viewModel.setAdapter(adapter)
        observeViewModel()
        setupClickListeners()
    }

    private fun initializeViews() {
        recyclerView = activity.findViewById(R.id.recycler_view)
        loadingProgress = activity.findViewById(R.id.loading_progress)
        adapter = ItemAdapter(emptyList())

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        activity.lifecycleScope.launch {
            activity.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
                adapter.updateBets(state.bets)
            }

            is BetsUiState.Error -> {
                setLoading(false)
                Toast.makeText(activity, state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupClickListeners() {
        val updateOddsButton = activity.findViewById<Button>(R.id.update_odds_button)
        updateOddsButton.setOnClickListener {
            viewModel.updateOdds()
        }
    }

    private fun setLoading(isLoading: Boolean) {
        loadingProgress.visibility = if (isLoading) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}
