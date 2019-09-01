package com.vincent.landing.fact_list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vincent.network.models.Fact
import kotlinx.android.synthetic.main.card_fact.view.*

internal class FactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

   fun bindFact(fact: Fact) {
       itemView.tv_fact.text = fact.body
   }
}