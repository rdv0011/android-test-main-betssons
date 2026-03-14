package com.betsson.interviewtest.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.betsson.interviewtest.R
import com.betsson.interviewtest.presentation.ui.controller.BetsUIController
import com.betsson.interviewtest.presentation.viewmodel.BetsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: BetsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val uiController = BetsUIController(this, viewModel)
        uiController.initialize()
    }
}
