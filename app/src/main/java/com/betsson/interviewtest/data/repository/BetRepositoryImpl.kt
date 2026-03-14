package com.betsson.interviewtest.data.repository

import com.betsson.interviewtest.data.datasource.BetLocalDataSource
import com.betsson.interviewtest.domain.model.Bet
import com.betsson.interviewtest.domain.repository.BetRepository
import com.betsson.interviewtest.utils.OddsCalculator
import kotlinx.coroutines.delay
import javax.inject.Inject

class BetRepositoryImpl @Inject constructor(
    private val localDataSource: BetLocalDataSource,
    private val oddsCalculator: OddsCalculator
) : BetRepository {
    
    override suspend fun fetchBets(): List<Bet> {
        return localDataSource.fetchBets()
    }
    
    override suspend fun updateBetsOdds(bets: List<Bet>): List<Bet> {
        return oddsCalculator.calculateOdds(bets)
    }
}
