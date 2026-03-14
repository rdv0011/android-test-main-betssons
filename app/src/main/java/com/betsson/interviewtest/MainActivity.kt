package com.betsson.interviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val bets = arrayListOf<Bet>()
    private lateinit var adapter: ItemAdapter
    private val oddsCalculator = OddsCalculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        adapter = ItemAdapter(getItemsFromNetwork())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.update_odds_button).setOnClickListener {
            updateOdds()
        }
    }

    private fun getItemsFromNetwork(): List<Bet> {
        bets.add(Bet("Winning team", 10, 20, "https://i.imgur.com/mx66SBD.jpeg"))
        bets.add(Bet("Total score", 2, 0, "https://i.imgur.com/VnPRqcv.jpeg"))
        bets.add(Bet("Player performance", 5, 7, "https://i.imgur.com/Urpc00H.jpeg"))
        bets.add(Bet("First goal scorer", 0, 80, "https://i.imgur.com/Wy94Tt7.jpeg"))
        bets.add(Bet("Number of fouls", 5, 49, "https://i.imgur.com/NMLpcKj.jpeg"))
        bets.add(Bet("Corner kicks", 3, 6, "https://i.imgur.com/TiJ8y5l.jpeg"))
        
        return bets.sortedBy { it.sellIn }.toList()
    }

    private fun updateOdds() {
        oddsCalculator.calculateOdds(bets)
        
        val sortedBets = bets.sortedBy { it.sellIn }
        bets.clear()
        bets.addAll(sortedBets)
        
        adapter.updateBets(bets)
    }
}