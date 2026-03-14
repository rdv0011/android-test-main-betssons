package com.betsson.interviewtest.presentation.ui.controller

import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.betsson.interviewtest.R
import com.betsson.interviewtest.presentation.ui.manager.BetsContentManager
import com.betsson.interviewtest.presentation.viewmodel.BetsViewModel

class BetsUIController(
    private val activity: AppCompatActivity,
    private val viewModel: BetsViewModel,
    private val contentManager: BetsContentManager
) {

    fun initialize() {
        observeViewModel()
        setupClickListeners()
    }

    private fun observeViewModel() {
        viewModel.bets.observe(activity, Observer { bets ->
            contentManager.updateBets(bets)
        })

        viewModel.isLoading.observe(activity, Observer { isLoading ->
            contentManager.setLoading(isLoading)
        })

        viewModel.error.observe(activity, Observer { error ->
            if (error != null) {
            }
        })
    }

    private fun setupClickListeners() {
        val updateOddsButton = activity.findViewById<Button>(R.id.update_odds_button)
        updateOddsButton.setOnClickListener {
            viewModel.updateOdds()
        }
    }
}
