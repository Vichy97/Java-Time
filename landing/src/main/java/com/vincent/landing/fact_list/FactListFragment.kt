package com.vincent.landing.fact_list

import android.os.Bundle
import android.view.View

import com.vincent.core.ui.BaseFragment
import com.vincent.landing.R

import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject

import kotlinx.android.synthetic.main.fragment_fact_list.*

internal class FactListFragment : BaseFragment(R.layout.fragment_fact_list, factListModule) {

    private val viewModel: FactListViewModel by viewModel()
    private val factListAdapter: FactListAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupAdapter() {
        rv_fact_list.adapter = factListAdapter
    }
}