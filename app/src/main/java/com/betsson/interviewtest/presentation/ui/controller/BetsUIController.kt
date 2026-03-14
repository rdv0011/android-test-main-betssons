package com.betsson.interviewtest.presentation.ui.controller

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.betsson.interviewtest.R
import com.betsson.interviewtest.presentation.ui.adapter.ItemAdapter
import com.betsson.interviewtest.presentation.viewmodel.BetsViewModel

class BetsUIController(
    private val activity: AppCompatActivity,
    private val viewModel: BetsViewModel,
    private val recyclerView: RecyclerView,
    private val loadingProgress: ProgressBar,
    private val adapter: ItemAdapter
) {

    fun initialize() {
        viewModel.setAdapter(adapter)
        observeViewModel()
        setupClickListeners()
    }

    private fun observeViewModel() {
        viewModel.bets.observe(activity, Observer { bets ->
            viewModel.updateAdapterBets(bets)
        })

        viewModel.isLoading.observe(activity, Observer { isLoading ->
            viewModel.setShowContent(!isLoading)
        })

        viewModel.showContent.observe(activity, Observer { showContent ->
            setLoading(!showContent)
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
