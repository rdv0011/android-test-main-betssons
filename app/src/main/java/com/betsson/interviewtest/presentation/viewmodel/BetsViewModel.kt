package com.betsson.interviewtest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.betsson.interviewtest.domain.model.Bet
import com.betsson.interviewtest.domain.usecase.FetchBetsUseCase
import com.betsson.interviewtest.domain.usecase.UpdateBetsOddsUseCase
import com.betsson.interviewtest.presentation.ui.adapter.ItemAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BetsViewModel @Inject constructor(
    private val fetchBetsUseCase: FetchBetsUseCase,
    private val updateBetsOddsUseCase: UpdateBetsOddsUseCase
) : ViewModel() {
    
    private val _bets = MutableLiveData<List<Bet>>()
    val bets: LiveData<List<Bet>> = _bets
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    private val _showContent = MutableLiveData<Boolean>()
    val showContent: LiveData<Boolean> = _showContent
    
    private var adapter: ItemAdapter? = null
    
    init {
        loadBets()
    }
    
    fun setAdapter(adapter: ItemAdapter) {
        this.adapter = adapter
    }
    
    private fun loadBets() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val fetchedBets = fetchBetsUseCase()
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
                val updatedBets = updateBetsOddsUseCase(currentBets)
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
    
    fun updateAdapterBets(bets: List<Bet>) {
        adapter?.updateBets(bets)
    }
    
    fun setShowContent(show: Boolean) {
        _showContent.value = show
    }
}
