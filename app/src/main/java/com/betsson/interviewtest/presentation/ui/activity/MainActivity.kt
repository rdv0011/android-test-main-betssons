package com.betsson.interviewtest.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.betsson.interviewtest.R
import com.betsson.interviewtest.presentation.ui.adapter.ItemAdapter
import com.betsson.interviewtest.presentation.ui.controller.BetsUIController
import com.betsson.interviewtest.presentation.viewmodel.BetsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: BetsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val loadingProgress = findViewById<ProgressBar>(R.id.loading_progress)
        val adapter = ItemAdapter(emptyList())

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val uiController = BetsUIController(this, viewModel, recyclerView, loadingProgress, adapter)
        uiController.initialize()
    }
}
