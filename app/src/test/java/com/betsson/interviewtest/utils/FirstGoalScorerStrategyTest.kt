package com.betsson.interviewtest.utils

import com.betsson.interviewtest.domain.model.Bet
import com.betsson.interviewtest.domain.service.FirstGoalScorerStrategy
import org.junit.Test
import kotlin.test.assertEquals

class FirstGoalScorerStrategyTest {
    private val strategy = FirstGoalScorerStrategy()

    @Test
    fun `bet remains unchanged`() {
        val bet = Bet(type = "First goal scorer", sellIn = 10, odds = 20, image = "")
        val result = strategy.updateOdds(bet)
        assertEquals(bet, result)
    }

    @Test
    fun `bet remains unchanged even after expiry`() {
        val bet = Bet(type = "First goal scorer", sellIn = -5, odds = 20, image = "")
        val result = strategy.updateOdds(bet)
        assertEquals(bet, result)
    }

    @Test
    fun `bet with zero odds remains unchanged`() {
        val bet = Bet(type = "First goal scorer", sellIn = 10, odds = 0, image = "")
        val result = strategy.updateOdds(bet)
        assertEquals(bet, result)
    }

    @Test
    fun `bet with max odds remains unchanged`() {
        val bet = Bet(type = "First goal scorer", sellIn = 10, odds = 50, image = "")
        val result = strategy.updateOdds(bet)
        assertEquals(bet, result)
    }
}
