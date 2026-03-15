package com.betsson.interviewtest.presentation.viewmodel

import com.betsson.interviewtest.domain.model.Bet
import com.betsson.interviewtest.domain.repository.BetRepository
import com.betsson.interviewtest.domain.usecase.FetchBetsUseCase
import com.betsson.interviewtest.domain.usecase.UpdateBetsOddsUseCase
import com.betsson.interviewtest.presentation.state.BetsUiState
import com.betsson.interviewtest.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class BetsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var betRepository: BetRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `loadBets transitions to Success on success`() = runTest {
        val bets = listOf(
            Bet(type = "Team A", sellIn = 20, odds = 10, image = ""),
            Bet(type = "Team B", sellIn = 10, odds = 20, image = "")
        )
        whenever(betRepository.fetchBets()).thenReturn(bets)
        whenever(betRepository.updateBetsOdds(bets)).thenReturn(bets)

        val fetchUseCase = FetchBetsUseCase(betRepository)
        val updateUseCase = UpdateBetsOddsUseCase(betRepository)
        val viewModel = BetsViewModel(fetchUseCase, updateUseCase)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertIs<BetsUiState.Success>(state)
        assertEquals(2, state.bets.size)
    }

    @Test
    fun `loadBets sorts bets by sellIn`() = runTest {
        val bets = listOf(
            Bet(type = "Team A", sellIn = 30, odds = 10, image = ""),
            Bet(type = "Team B", sellIn = 10, odds = 20, image = ""),
            Bet(type = "Team C", sellIn = 20, odds = 15, image = "")
        )
        whenever(betRepository.fetchBets()).thenReturn(bets)
        whenever(betRepository.updateBetsOdds(bets)).thenReturn(bets)

        val fetchUseCase = FetchBetsUseCase(betRepository)
        val updateUseCase = UpdateBetsOddsUseCase(betRepository)
        val viewModel = BetsViewModel(fetchUseCase, updateUseCase)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertIs<BetsUiState.Success>(state)
        assertEquals(listOf(10, 20, 30), state.bets.map { it.sellIn })
    }

    @Test
    fun `updateOdds transitions from Success to Success with updated bets`() = runTest {
        val originalBets = listOf(
            Bet(type = "Team A", sellIn = 20, odds = 10, image = "")
        )
        val updatedBets = listOf(
            Bet(type = "Team A", sellIn = 19, odds = 9, image = "")
        )
        whenever(betRepository.fetchBets()).thenReturn(originalBets)
        whenever(betRepository.updateBetsOdds(originalBets)).thenReturn(updatedBets)

        val fetchUseCase = FetchBetsUseCase(betRepository)
        val updateUseCase = UpdateBetsOddsUseCase(betRepository)
        val viewModel = BetsViewModel(fetchUseCase, updateUseCase)
        advanceUntilIdle()

        viewModel.updateOdds()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertIs<BetsUiState.Success>(state)
        assertEquals(updatedBets[0].odds, state.bets[0].odds)
        assertEquals(updatedBets[0].sellIn, state.bets[0].sellIn)
    }
}
