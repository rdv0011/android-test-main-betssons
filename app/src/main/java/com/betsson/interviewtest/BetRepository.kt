package com.betsson.interviewtest

class BetRepository {
    private val oddsCalculator = OddsCalculator()
    
    fun fetchBets(): List<Bet> {
        val bets = arrayListOf<Bet>()
        bets.add(Bet("Winning team", 10, 20, "https://i.imgur.com/mx66SBD.jpeg"))
        bets.add(Bet("Total score", 2, 0, "https://i.imgur.com/VnPRqcv.jpeg"))
        bets.add(Bet("Player performance", 5, 7, "https://i.imgur.com/Urpc00H.jpeg"))
        bets.add(Bet("First goal scorer", 0, 80, "https://i.imgur.com/Wy94Tt7.jpeg"))
        bets.add(Bet("Number of fouls", 5, 49, "https://i.imgur.com/NMLpcKj.jpeg"))
        bets.add(Bet("Corner kicks", 3, 6, "https://i.imgur.com/TiJ8y5l.jpeg"))
        
        return bets.sortedBy { it.sellIn }
    }
    
    fun updateBetsOdds(bets: List<Bet>): List<Bet> {
        oddsCalculator.calculateOdds(bets)
        return bets.sortedBy { it.sellIn }
    }
}
