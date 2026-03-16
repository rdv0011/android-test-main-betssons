package com.betsson.interviewtest.data.repository

import com.betsson.interviewtest.data.datasource.BetLocalDataSource
import com.betsson.interviewtest.domain.model.Bet
import com.betsson.interviewtest.domain.repository.BetRepository
import javax.inject.Inject

class BetRepositoryImpl @Inject constructor(
    private val localDataSource: BetLocalDataSource
) : BetRepository {
    
    override suspend fun fetchBets(): List<Bet> {
        return localDataSource.fetchBets()
    }
}
