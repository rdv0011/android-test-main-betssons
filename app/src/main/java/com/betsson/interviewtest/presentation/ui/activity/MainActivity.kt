package com.betsson.interviewtest.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.betsson.interviewtest.R
import com.betsson.interviewtest.presentation.ui.adapter.ItemAdapter
import com.betsson.interviewtest.presentation.viewmodel.BetsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: BetsViewModel by viewModels()
    private lateinit var adapter: ItemAdapter
    private lateinit var loadingProgress: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadingProgress = findViewById(R.id.loading_progress)
        recyclerView = findViewById(R.id.recycler_view)

        setupRecyclerView()
        observeViewModel()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        adapter = ItemAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        viewModel.bets.observe(this, Observer { bets ->
            adapter.updateBets(bets)
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading) {
                loadingProgress.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                loadingProgress.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
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
