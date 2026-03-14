package com.betsson.interviewtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.betsson.interviewtest.domain.model.Bet
import com.betsson.interviewtest.domain.usecase.FetchBetsUseCase
import com.betsson.interviewtest.domain.usecase.UpdateBetsOddsUseCase
import com.betsson.interviewtest.presentation.state.BetsUiState
import com.betsson.interviewtest.presentation.ui.adapter.ItemAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BetsViewModel @Inject constructor(
    private val fetchBetsUseCase: FetchBetsUseCase,
    private val updateBetsOddsUseCase: UpdateBetsOddsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<BetsUiState>(BetsUiState.Loading)
    val uiState: StateFlow<BetsUiState> = _uiState.asStateFlow()
    
    private var adapter: ItemAdapter? = null
    
    init {
        loadBets()
    }
    
    fun setAdapter(adapter: ItemAdapter) {
        this.adapter = adapter
    }
    
    private fun loadBets() {
        viewModelScope.launch {
            _uiState.value = BetsUiState.Loading
            try {
                val fetchedBets = fetchBetsUseCase()
                _uiState.value = BetsUiState.Success(fetchedBets)
            } catch (e: Exception) {
                _uiState.value = BetsUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
    
    fun updateOdds() {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState !is BetsUiState.Success) return@launch
            
            _uiState.value = BetsUiState.Loading
            try {
                val updatedBets = updateBetsOddsUseCase(currentState.bets)
                _uiState.value = BetsUiState.Success(updatedBets)
            } catch (e: Exception) {
                _uiState.value = BetsUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
