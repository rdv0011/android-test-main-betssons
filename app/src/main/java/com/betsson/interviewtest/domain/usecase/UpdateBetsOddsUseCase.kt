package com.betsson.interviewtest.domain.usecase

import com.betsson.interviewtest.domain.model.Bet
import com.betsson.interviewtest.domain.repository.BetRepository
import javax.inject.Inject

class UpdateBetsOddsUseCase @Inject constructor(
    private val repository: BetRepository
) {
    suspend operator fun invoke(bets: List<Bet>): List<Bet> {
        return repository.updateBetsOdds(bets)
    }
}
