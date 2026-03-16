package com.betsson.interviewtest.domain.service

import com.betsson.interviewtest.domain.model.Bet

interface OddsUpdateStrategy {
    fun updateOdds(bet: Bet): Bet
}

class DefaultBetStrategy : OddsUpdateStrategy {
    override fun updateOdds(bet: Bet): Bet {
        // Default behavior for regular bets
        val newOdds = if (bet.odds > 0) bet.odds - 1 else bet.odds
        val newSellIn = bet.sellIn - 1
        
        val finalOdds = if (newSellIn < 0 && newOdds > 0) {
            newOdds - 1
        } else {
            newOdds
        }
        
        return bet.copy(odds = finalOdds, sellIn = newSellIn)
    }
}

class FirstGoalScorerStrategy : OddsUpdateStrategy {
    override fun updateOdds(bet: Bet): Bet {
        // First goal scorer never decreases in sellIn or odds
        return bet
    }
}

class TotalScoreBetStrategy : OddsUpdateStrategy {
    override fun updateOdds(bet: Bet): Bet {
        // Total score increases in value
        val newOdds = if (bet.odds < 50) bet.odds + 1 else bet.odds
        val newSellIn = bet.sellIn - 1
        
        val finalOdds = if (newSellIn < 0) {
            // After sell in, increases even more
            if (newOdds < 50) newOdds + 1 else newOdds
        } else {
            newOdds
        }
        
        return bet.copy(odds = finalOdds, sellIn = newSellIn)
    }
}

class NumberOfFoulsStrategy : OddsUpdateStrategy {
    override fun updateOdds(bet: Bet): Bet {
        // Number of fouls has special behavior
        var newOdds = bet.odds
        
        if (newOdds < 50) {
            newOdds += 1
        }

        if (bet.sellIn < 11 && newOdds < 50) {
            newOdds += 1
        }

        if (bet.sellIn < 6 && newOdds < 50) {
            newOdds += 1
        }

        val newSellIn = bet.sellIn - 1
        
        val finalOdds = if (newSellIn < 0) 0 else newOdds
        
        return bet.copy(odds = finalOdds, sellIn = newSellIn)
    }
}

class OddsCalculator {
    private val strategies = mapOf(
        "First goal scorer" to FirstGoalScorerStrategy(),
        "Total score" to TotalScoreBetStrategy(),
        "Number of fouls" to NumberOfFoulsStrategy()
    )

    fun calculateOdds(bets: List<Bet>): List<Bet> {
        return bets.map { bet ->
            val strategy = strategies[bet.type] ?: DefaultBetStrategy()
            strategy.updateOdds(bet)
        }
    }
}
