package com.betsson.interviewtest.presentation.ui.manager

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.betsson.interviewtest.domain.model.Bet
import com.betsson.interviewtest.presentation.ui.adapter.ItemAdapter

class BetsContentManager(
    private val recyclerView: RecyclerView,
    private val loadingProgress: ProgressBar,
    private val adapter: ItemAdapter
) {

    fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    fun updateBets(bets: List<Bet>) {
        adapter.updateBets(bets)
    }

    private fun showLoading() {
        loadingProgress.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    private fun hideLoading() {
        loadingProgress.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }
}
