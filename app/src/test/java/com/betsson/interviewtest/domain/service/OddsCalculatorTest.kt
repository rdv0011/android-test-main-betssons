package com.betsson.interviewtest.domain.service

import com.betsson.interviewtest.domain.model.Bet
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class OddsCalculatorTest {
    private lateinit var calculator: OddsCalculator

    @Before
    fun setup() {
        calculator = OddsCalculator()
    }

    @Test
    fun `empty list returns empty list`() {
        val result = calculator.calculateOdds(emptyList())
        assertTrue(result.isEmpty())
    }

    @Test
    fun `first goal scorer routes to FirstGoalScorerStrategy`() {
        val bet = Bet(type = "First goal scorer", sellIn = 10, odds = 20, image = "")
        val result = calculator.calculateOdds(listOf(bet))
        assertEquals(1, result.size)
        assertEquals(bet, result[0])
    }

    @Test
    fun `total score routes to TotalScoreBetStrategy`() {
        val bet = Bet(type = "Total score", sellIn = 10, odds = 20, image = "")
        val result = calculator.calculateOdds(listOf(bet))
        assertEquals(1, result.size)
        assertEquals(21, result[0].odds)
        assertEquals(9, result[0].sellIn)
    }

    @Test
    fun `number of fouls routes to NumberOfFoulsStrategy`() {
        val bet = Bet(type = "Number of fouls", sellIn = 10, odds = 20, image = "")
        val result = calculator.calculateOdds(listOf(bet))
        assertEquals(1, result.size)
        assertEquals(22, result[0].odds)
        assertEquals(9, result[0].sellIn)
    }

    @Test
    fun `unknown type routes to DefaultBetStrategy`() {
        val bet = Bet(type = "Winning team", sellIn = 10, odds = 20, image = "")
        val result = calculator.calculateOdds(listOf(bet))
        assertEquals(1, result.size)
        assertEquals(19, result[0].odds)
        assertEquals(9, result[0].sellIn)
    }

    @Test
    fun `mixed bet types process correctly`() {
        val bets = listOf(
            Bet(type = "First goal scorer", sellIn = 10, odds = 20, image = ""),
            Bet(type = "Total score", sellIn = 10, odds = 20, image = ""),
            Bet(type = "Winning team", sellIn = 10, odds = 20, image = "")
        )
        val result = calculator.calculateOdds(bets)
        assertEquals(3, result.size)
        assertEquals(20, result[0].odds)
        assertEquals(21, result[1].odds)
        assertEquals(19, result[2].odds)
    }
}
