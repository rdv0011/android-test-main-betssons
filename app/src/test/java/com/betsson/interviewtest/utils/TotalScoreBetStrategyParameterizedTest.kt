package com.betsson.interviewtest.utils

import com.betsson.interviewtest.domain.model.Bet
import com.betsson.interviewtest.domain.service.TotalScoreBetStrategy
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.Assert.assertEquals

@RunWith(Parameterized::class)
class TotalScoreBetStrategyParameterizedTest(
    private val inputOdds: Int,
    private val inputSellIn: Int,
    private val expectedOdds: Int,
    private val expectedSellIn: Int,
    private val description: String
) {
    private val strategy = TotalScoreBetStrategy()

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{4}")
        fun data() = listOf(
            arrayOf(20, 10, 21, 9, "normal: odds increase by 1 when sellIn > 0"),
            arrayOf(20, 1, 21, 0, "before expiry: odds increase by 1"),
            arrayOf(20, 0, 22, -1, "expired: odds increase by 2 (double increase)"),
            arrayOf(49, 0, 50, -1, "cap at expiry: 49 + 2 = 51, capped at 50"),
            arrayOf(49, 10, 50, 9, "cap before expiry: 49 + 1 = 50, then capped"),
            arrayOf(50, 10, 50, 9, "max odds: odds = 50 stays at 50"),
            arrayOf(50, 0, 50, -1, "max odds at expiry: stays at 50"),
            arrayOf(0, 10, 1, 9, "low odds: 0 + 1 = 1"),
            arrayOf(0, 0, 2, -1, "low odds expired: 0 + 2 = 2"),
            arrayOf(48, 0, 50, -1, "near cap expired: 48 + 2 = 50")
        )
    }

    @Test
    fun `updateOdds produces expected result`() {
        val bet = Bet(type = "Total score", sellIn = inputSellIn, odds = inputOdds, image = "")
        val result = strategy.updateOdds(bet)
        assertEquals("Expected odds=$expectedOdds for: $description", expectedOdds, result.odds)
        assertEquals("Expected sellIn=$expectedSellIn for: $description", expectedSellIn, result.sellIn)
    }
}
