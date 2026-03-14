package com.betsson.interviewtest.data.datasource

import com.betsson.interviewtest.domain.model.Bet
import kotlinx.coroutines.delay
import javax.inject.Inject

class BetLocalDataSource @Inject constructor() {

    suspend fun fetchBets(): List<Bet> {
        delay(2000)
        return listOf(
            Bet(
                type = "Winning team",
                sellIn = 10,
                odds = 20,
                image = "https://i.imgur.com/mx66SBD.jpeg"
            ),
            Bet(
                type = "Total score",
                sellIn = 2,
                odds = 0,
                image = "https://i.imgur.com/VnPRqcv.jpeg"
            ),
            Bet(
                type = "Player performance",
                sellIn = 5,
                odds = 7,
                image = "https://i.imgur.com/Urpc00H.jpeg"
            ),
            Bet(
                type = "First goal scorer",
                sellIn = 0,
                odds = 80,
                image = "https://i.imgur.com/Wy94Tt7.jpeg"
            ),
            Bet(
                type = "Number of fouls",
                sellIn = 5,
                odds = 49,
                image = "https://i.imgur.com/NMLpcKj.jpeg"
            ),
            Bet(
                type = "Corner kicks",
                sellIn = 3,
                odds = 6,
                image = "https://i.imgur.com/TiJ8y5l.jpeg"
            )
        )
    }
}
