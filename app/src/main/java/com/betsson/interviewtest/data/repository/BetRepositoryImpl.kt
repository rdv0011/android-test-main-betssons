package com.betsson.interviewtest.data.repository

import com.betsson.interviewtest.domain.model.Bet
import com.betsson.interviewtest.domain.repository.BetRepository
import com.betsson.interviewtest.utils.OddsCalculator
import javax.inject.Inject

class BetRepositoryImpl @Inject constructor(
    private val oddsCalculator: OddsCalculator
) : BetRepository {
    
    override fun fetchBets(): List<Bet> {
        val bets = arrayListOf<Bet>()
        bets.add(Bet(
            type = "Winning team",
            sellIn = 10,
            odds = 20,
            image = "https://i.imgur.com/mx66SBD.jpeg"
        ))
        bets.add(Bet(
            type = "Total score",
            sellIn = 2,
            odds = 0,
            image = "https://i.imgur.com/VnPRqcv.jpeg"
        ))
        bets.add(Bet(
            type = "Player performance",
            sellIn = 5,
            odds = 7,
            image = "https://i.imgur.com/Urpc00H.jpeg"
        ))
        bets.add(Bet(
            type = "First goal scorer",
            sellIn = 0,
            odds = 80,
            image = "https://i.imgur.com/Wy94Tt7.jpeg"
        ))
        bets.add(Bet(
            type = "Number of fouls",
            sellIn = 5,
            odds = 49,
            image = "https://i.imgur.com/NMLpcKj.jpeg"
        ))
        bets.add(Bet(
            type = "Corner kicks",
            sellIn = 3,
            odds = 6,
            image = "https://i.imgur.com/TiJ8y5l.jpeg"
        ))
        
        return bets
    }
    
    override fun updateBetsOdds(bets: List<Bet>): List<Bet> {
        return oddsCalculator.calculateOdds(bets)
    }
}
