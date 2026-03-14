package com.betsson.interviewtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class ItemAdapter(private var bets: List<Bet>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(bet: Bet) {
            view.findViewById<TextView>(R.id.bet_name).text = bet.type
            view.findViewById<TextView>(R.id.bet_sell_in).text = view.context.getString(
                R.string.bet_sell_in_format,
                bet.sellIn
            )
            view.findViewById<TextView>(R.id.bet_odds).text = view.context.getString(
                R.string.bet_odds_format,
                bet.odds
            )
            
            view.findViewById<ImageView>(R.id.bet_image).load(bet.image) {
                crossfade(true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false) as View
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bets.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = bets[position]
        holder.bind(item)
    }

    fun updateBets(newBets: List<Bet>) {
        bets = newBets
        notifyDataSetChanged()
    }
}
