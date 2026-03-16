package com.betsson.interviewtest.domain.usecase

import com.betsson.interviewtest.domain.model.Bet
import com.betsson.interviewtest.domain.service.OddsCalculator
import javax.inject.Inject

class UpdateBetsOddsUseCase @Inject constructor(
    private val oddsCalculator: OddsCalculator
) {
    suspend operator fun invoke(bets: List<Bet>): List<Bet> {
        return oddsCalculator.calculateOdds(bets)
    }
}
