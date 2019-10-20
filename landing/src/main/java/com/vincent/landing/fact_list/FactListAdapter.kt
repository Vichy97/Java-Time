package com.vincent.landing.fact_list

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import com.vincent.domain.model.Fact

import com.vincent.landing.R

internal class FactListAdapter : RecyclerView.Adapter<FactViewHolder>() {

    private val facts = mutableListOf<Fact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_fact, parent, false)
        return FactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.bindFact(facts[position])
    }

    override fun getItemCount(): Int {
        return facts.size
    }

    fun setFacts(facts: List<Fact>) {
        this.facts.clear()
        this.facts.addAll(facts)
        notifyDataSetChanged()
    }
}