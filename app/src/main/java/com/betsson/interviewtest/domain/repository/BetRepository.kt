package com.betsson.interviewtest.domain.repository

import com.betsson.interviewtest.domain.model.Bet

interface BetRepository {
    suspend fun fetchBets(): List<Bet>
}
