package com.vincent.landing.fact_list

import android.os.Bundle
import android.view.MenuItem
import android.view.View

import com.vincent.core.ui.BaseMvvmFragment
import com.vincent.landing.R

import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject

import kotlinx.android.synthetic.main.fragment_fact_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
internal class FactListFragment : BaseMvvmFragment<FactListViewState>(
    layoutId = R.layout.fragment_fact_list,
    menuId = R.menu.menu_fact_list,
    module = factListModule
) {

    override val viewModel: FactListViewModel by viewModel()
    private val factListAdapter: FactListAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()
        setupViewEvents()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupViewEvents() {
        fab.setOnClickListener { viewModel.onFloatingActionButtonClicked() }
        swipe_container.setOnRefreshListener { viewModel.onSwipeToRefresh() }
    }

    private fun setupAdapter() {
        vp_fact_list.adapter = factListAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_about -> {
                viewModel.onAboutClicked()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onViewStateEvent(viewState: FactListViewState) {
        factListAdapter.setFacts(viewState.facts)
    }

    override fun showLoading(loading: Boolean) {
        super.showLoading(loading)

        swipe_container.isRefreshing = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()

        vp_fact_list.adapter = null
    }
}