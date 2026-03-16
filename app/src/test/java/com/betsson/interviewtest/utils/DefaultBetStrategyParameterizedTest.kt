package com.betsson.interviewtest.utils

import com.betsson.interviewtest.domain.model.Bet
import com.betsson.interviewtest.domain.service.DefaultBetStrategy
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.Assert.assertEquals

@RunWith(Parameterized::class)
class DefaultBetStrategyParameterizedTest(
    private val inputOdds: Int,
    private val inputSellIn: Int,
    private val expectedOdds: Int,
    private val expectedSellIn: Int,
    private val description: String
) {
    private val strategy = DefaultBetStrategy()

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{4}")
        fun data() = listOf(
            arrayOf(20, 10, 19, 9, "normal: odds decrease by 1"),
            arrayOf(1, 10, 0, 9, "floor: odds = 1 decreases to 0"),
            arrayOf(0, 10, 0, 9, "floor: odds = 0 stays at 0"),
            arrayOf(20, 1, 19, 0, "before expiry: normal decrease"),
            arrayOf(20, 0, 18, -1, "expired: odds decrease by 2 (double degradation)"),
            arrayOf(1, 0, 0, -1, "expired with odds = 1: goes to 0 (not -1)"),
            arrayOf(0, 0, 0, -1, "expired with odds = 0: stays at 0"),
            arrayOf(50, -5, 48, -6, "deeply expired: odds continue to decrease by 2"),
            arrayOf(2, -1, 0, -2, "deeply expired with low odds: odds decrease to 0"),
            arrayOf(0, -1, 0, -2, "deeply expired with odds = 0: stays at 0")
        )
    }

    @Test
    fun `updateOdds produces expected result`() {
        val bet = Bet(type = "Winning team", sellIn = inputSellIn, odds = inputOdds, image = "")
        val result = strategy.updateOdds(bet)
        assertEquals("Expected odds=$expectedOdds for: $description", expectedOdds, result.odds)
        assertEquals("Expected sellIn=$expectedSellIn for: $description", expectedSellIn, result.sellIn)
    }
}
