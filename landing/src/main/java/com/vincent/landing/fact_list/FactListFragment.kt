package com.vincent.landing.fact_list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View

import com.vincent.core.ui.BaseFragment
import com.vincent.landing.R

import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject

import kotlinx.android.synthetic.main.fragment_fact_list.*

internal class FactListFragment : BaseFragment(R.layout.fragment_fact_list, factListModule) {

    private val viewModel: FactListViewModel by viewModel()
    private val factListAdapter: FactListAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        viewModel.start()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()
        setupViewEvents()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupViewEvents() {
        fab.setOnClickListener {
            viewModel.onFloatingActionButtonClicked()
        }
        swipe_container.setOnRefreshListener {
            viewModel.onSwipeToRefresh()
        }
    }

    private fun setupAdapter() {
        vp_fact_list.adapter = factListAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fact_list, menu)
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

    override fun onStart() {
        super.onStart()

        subscribeToViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        vp_fact_list.adapter = null
    }

    private fun subscribeToViewModel() {
        val viewStateDisposable = viewModel.viewStateEvents
            .observeOn(rxProvider.uiScheduler())
            .subscribe { onViewStateReceived(it) }
        val snackBarDisposable = viewModel.snackbarEvents
            .observeOn(rxProvider.uiScheduler())
            .subscribe { showSnackbar(it) }
        val loadingDisposable = viewModel.loadingEvents
            .observeOn(rxProvider.uiScheduler())
            .subscribe { showLoading(it) }
        val navigationDisposable = viewModel.navigationEvents
            .observeOn(rxProvider.uiScheduler())
            .subscribe { navigate(it) }

        addDisposables(
            viewStateDisposable,
            snackBarDisposable,
            loadingDisposable,
            navigationDisposable
        )
    }

    private fun onViewStateReceived(viewState: FactListViewState) {
        factListAdapter.setFacts(viewState.facts)
    }

    private fun showLoading(loading: Boolean) {
        swipe_container.isRefreshing = loading
        view?.isClickable = !loading
    }
}