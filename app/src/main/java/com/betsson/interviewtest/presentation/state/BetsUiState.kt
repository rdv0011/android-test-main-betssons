package com.betsson.interviewtest.presentation.state

import com.betsson.interviewtest.domain.model.Bet

sealed interface BetsUiState {
    object Loading : BetsUiState
    data class Success(val bets: List<Bet>) : BetsUiState
    data class Error(val message: String) : BetsUiState
}
