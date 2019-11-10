package com.vincent.ui.fact_list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vincent.domain.model.Fact

import kotlinx.android.synthetic.main.layout_fact.view.*

internal class FactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

   fun bindFact(fact: Fact) {
       itemView.tv_fact.text = fact.body
   }
}