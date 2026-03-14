package com.betsson.interviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val viewModel: BetsViewModel by viewModels()
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        observeViewModel()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        adapter = ItemAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        viewModel.bets.observe(this, Observer { bets ->
            adapter.updateBets(bets)
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
