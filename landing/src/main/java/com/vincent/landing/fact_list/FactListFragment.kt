package com.vincent.landing.fact_list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible

import com.vincent.core.ui.BaseFragment
import com.vincent.landing.R

import io.reactivex.android.schedulers.AndroidSchedulers

import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject

import kotlinx.android.synthetic.main.fragment_fact_list.*

internal class FactListFragment : BaseFragment(R.layout.fragment_fact_list, factListModule) {

    private val viewModel: FactListViewModel by viewModel()
    private val factListAdapter: FactListAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()
        setupViewEvents()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupViewEvents() {
        fab.setOnClickListener {
            viewModel.onFloatingActionButtonClicked()
        }
    }

    private fun setupAdapter() {
        vp_fact_list.adapter = factListAdapter
    }

    override fun onStart() {
        super.onStart()

        subscribeToViewModel()
        viewModel.start(arguments)
    }

    private fun subscribeToViewModel() {
        val viewStateDisposable = viewModel.viewStateEvents
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { onViewStateReceived(it) }
        val snackBarDisposable = viewModel.snackbarEvents
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { showSnackbar(it) }
        val loadingDisposable = viewModel.loadingEvents
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { showLoading(it) }
        val navigationDisposable = viewModel.navigationEvents
            .observeOn(AndroidSchedulers.mainThread())
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
        progress_bar.isVisible = loading
        view?.isClickable = !loading
    }
}