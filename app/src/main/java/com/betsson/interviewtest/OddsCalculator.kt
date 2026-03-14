package com.betsson.interviewtest

interface OddsUpdateStrategy {
    fun updateOdds(bet: Bet)
}

class DefaultBetStrategy : OddsUpdateStrategy {
    override fun updateOdds(bet: Bet) {
        // Default behavior for regular bets
        if (bet.odds > 0) {
            bet.odds -= 1
        }
        bet.sellIn -= 1

        if (bet.sellIn < 0 && bet.odds > 0) {
            bet.odds -= 1
        }
    }
}

class FirstGoalScorerStrategy : OddsUpdateStrategy {
    override fun updateOdds(bet: Bet) {
        // First goal scorer never decreases in sellIn
        if (bet.odds > 0) {
            // odds don't decrease for first goal scorer
        }
        // sellIn never decreases for first goal scorer
    }
}

class TotalScoreBetStrategy : OddsUpdateStrategy {
    override fun updateOdds(bet: Bet) {
        // Total score increases in value
        if (bet.odds < 50) {
            bet.odds += 1
        }
        bet.sellIn -= 1

        if (bet.sellIn < 0) {
            // After sell in, increases even more
            if (bet.odds < 50) {
                bet.odds += 1
            }
        }
    }
}

class NumberOfFoulsStrategy : OddsUpdateStrategy {
    override fun updateOdds(bet: Bet) {
        // Number of fouls has special behavior
        if (bet.odds < 50) {
            bet.odds += 1
        }

        if (bet.sellIn < 11) {
            if (bet.odds < 50) {
                bet.odds += 1
            }
        }

        if (bet.sellIn < 6) {
            if (bet.odds < 50) {
                bet.odds += 1
            }
        }

        bet.sellIn -= 1

        if (bet.sellIn < 0) {
            bet.odds = 0
        }
    }
}

class OddsCalculator {
    private val strategies = mapOf(
        "First goal scorer" to FirstGoalScorerStrategy(),
        "Total score" to TotalScoreBetStrategy(),
        "Number of fouls" to NumberOfFoulsStrategy()
    )

    fun calculateOdds(bets: List<Bet>) {
        for (bet in bets) {
            val strategy = strategies[bet.type] ?: DefaultBetStrategy()
            strategy.updateOdds(bet)
        }
    }
}
