package com.betsson.interviewtest.utils

import com.betsson.interviewtest.domain.model.Bet
import com.betsson.interviewtest.domain.service.NumberOfFoulsStrategy
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.Assert.assertEquals

@RunWith(Parameterized::class)
class NumberOfFoulsStrategyParameterizedTest(
    private val inputOdds: Int,
    private val inputSellIn: Int,
    private val expectedOdds: Int,
    private val expectedSellIn: Int,
    private val description: String
) {
    private val strategy = NumberOfFoulsStrategy()

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{4}")
        fun data() = listOf(
            arrayOf(20, 15, 21, 14, "normal: odds increase by 1 when sellIn > 11"),
            arrayOf(20, 10, 22, 9, "threshold 11: odds increase by 2 when 6 <= sellIn <= 10"),
            arrayOf(20, 5, 23, 4, "threshold 6: odds increase by 3 when sellIn < 6"),
            arrayOf(49, 10, 50, 9, "cap: odds capped at 50 even with multiple increments"),
            arrayOf(50, 15, 50, 14, "max odds: odds = 50 stays at 50"),
            arrayOf(49, 5, 50, 4, "cap with threshold: 49 + 3 would be 52, capped at 50"),
            arrayOf(48, 5, 50, 4, "cap at boundary: 48 + 3 = 51, capped at 50"),
            arrayOf(20, 0, 0, -1, "expired: odds drop to 0 when sellIn passes 0"),
            arrayOf(30, 0, 0, -1, "expired with higher odds: still drops to 0"),
            arrayOf(20, 1, 23, 0, "approaching expiry: normal increment before expiry"),
            arrayOf(0, 15, 1, 14, "low odds normal: 0 + 1 = 1"),
            arrayOf(0, 5, 3, 4, "low odds threshold: 0 + 3 = 3")
        )
    }

    @Test
    fun `updateOdds produces expected result`() {
        val bet = Bet(type = "Number of fouls", sellIn = inputSellIn, odds = inputOdds, image = "")
        val result = strategy.updateOdds(bet)
        assertEquals("Expected odds=$expectedOdds for: $description", expectedOdds, result.odds)
        assertEquals("Expected sellIn=$expectedSellIn for: $description", expectedSellIn, result.sellIn)
    }
}
