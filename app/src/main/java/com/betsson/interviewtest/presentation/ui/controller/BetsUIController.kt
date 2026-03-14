package com.betsson.interviewtest.presentation.ui.controller

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
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
            viewModel.uiState.collect { state ->
                when (state) {
                    is BetsUiState.Loading -> {
                        setLoading(true)
                    }
                    is BetsUiState.Success -> {
                        setLoading(false)
                        adapter.updateBets(state.bets)
                    }
                    is BetsUiState.Error -> {
                        setLoading(false)
                    }
                }
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
        if (isLoading) {
            loadingProgress.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            loadingProgress.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
}
