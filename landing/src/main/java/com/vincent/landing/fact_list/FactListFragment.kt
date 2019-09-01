package com.vincent.landing.fact_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup

import com.vincent.core.ui.BaseFragment
import com.vincent.core.ui.BaseViewState
import com.vincent.landing.R

import kotlinx.android.synthetic.main.fragment_fact_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
internal class FactListFragment : BaseFragment(R.layout.fragment_fact_list, factListModule) {

    override val viewModel: FactListViewModel by viewModel()
    private val factListAdapter: FactListAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()
        fab.setOnClickListener {
            viewModel.onFloatingActionButtonClicked()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupAdapter() {
        rv_fact_list.adapter = factListAdapter
    }

    override fun onViewStateReceived(viewState: BaseViewState) {
        when (viewState) {
            is FactListViewState.LoadingState -> renderLoadingState()
            is FactListViewState.ContentState -> renderContentState(viewState)
            is FactListViewState.ErrorState -> renderErrorState(viewState)
        }
    }

    private fun renderLoadingState() {
        progress_bar.visibility = VISIBLE
    }

    private fun renderContentState(viewState: FactListViewState.ContentState) {
        progress_bar.visibility = INVISIBLE
        factListAdapter.setFacts(viewState.facts)
    }

    private fun renderErrorState(viewState: FactListViewState.ErrorState) {
        progress_bar.visibility = INVISIBLE
    }
}