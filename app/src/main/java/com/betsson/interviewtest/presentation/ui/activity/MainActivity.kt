package com.betsson.interviewtest.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.betsson.interviewtest.R
import com.betsson.interviewtest.presentation.ui.adapter.ItemAdapter
import com.betsson.interviewtest.presentation.ui.manager.BetsContentManager
import com.betsson.interviewtest.presentation.viewmodel.BetsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: BetsViewModel by viewModels()
    private lateinit var contentManager: BetsContentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val loadingProgress = findViewById<ProgressBar>(R.id.loading_progress)
        val adapter = ItemAdapter(emptyList())

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        contentManager = BetsContentManager(recyclerView, loadingProgress, adapter)

        observeViewModel()
        setupClickListeners()
    }

    private fun observeViewModel() {
        viewModel.bets.observe(this, Observer { bets ->
            contentManager.updateBets(bets)
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            contentManager.setLoading(isLoading)
        })

        viewModel.error.observe(this, Observer { error ->
            if (error != null) {
            }
        })
    }

    private fun setupClickListeners() {
        findViewById<Button>(R.id.update_odds_button).setOnClickListener {
            viewModel.updateOdds()
        }
    }
}
