package com.betsson.interviewtest.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.betsson.interviewtest.R
import com.betsson.interviewtest.domain.model.Bet

class ItemAdapter(private var bets: List<Bet>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val nameView = view.findViewById<TextView>(R.id.bet_name)
        private val sellInView = view.findViewById<TextView>(R.id.bet_sell_in)
        private val oddsView = view.findViewById<TextView>(R.id.bet_odds)
        private val imageView = view.findViewById<ImageView>(R.id.bet_image)

        fun bind(bet: Bet) {
            nameView.text = bet.type
            sellInView.text = view.context.getString(R.string.bet_sell_in_format, bet.sellIn)
            oddsView.text = view.context.getString(R.string.bet_odds_format, bet.odds)
            imageView.load(bet.image) {
                crossfade(true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = bets.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bets[position])
    }

    fun updateBets(newBets: List<Bet>) {
        val sortedBets = newBets.sortedBy { it.sellIn }
        bets = sortedBets
        notifyDataSetChanged()
    }
}
