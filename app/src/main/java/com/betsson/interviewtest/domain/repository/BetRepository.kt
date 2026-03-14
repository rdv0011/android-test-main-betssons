package com.betsson.interviewtest.domain.repository

import com.betsson.interviewtest.domain.model.Bet

interface BetRepository {
    fun fetchBets(): List<Bet>
    fun updateBetsOdds(bets: List<Bet>): List<Bet>
}
