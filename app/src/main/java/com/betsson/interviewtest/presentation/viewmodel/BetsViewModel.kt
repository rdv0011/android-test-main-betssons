package com.betsson.interviewtest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.betsson.interviewtest.domain.model.Bet
import com.betsson.interviewtest.domain.repository.BetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BetsViewModel @Inject constructor(
    private val repository: BetRepository
) : ViewModel() {
    
    private val _bets = MutableLiveData<List<Bet>>()
    val bets: LiveData<List<Bet>> = _bets
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    init {
        loadBets()
    }
    
    private fun loadBets() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val fetchedBets = repository.fetchBets()
                _bets.value = fetchedBets
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun updateOdds() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val currentBets = _bets.value ?: return@launch
                val updatedBets = repository.updateBetsOdds(currentBets)
                _bets.value = updatedBets
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun refreshBets() {
        loadBets()
    }
}
