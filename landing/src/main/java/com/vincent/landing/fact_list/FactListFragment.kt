package com.vincent.landing.fact_list

import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.jakewharton.rxbinding3.view.clicks

import com.vincent.core.ui.BaseFragment
import com.vincent.core.ui.ViewState
import com.vincent.landing.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject

import kotlinx.android.synthetic.main.fragment_fact_list.*

internal class FactListFragment : BaseFragment(R.layout.fragment_fact_list, factListModule) {

    private val viewModel: FactListViewModel by viewModel()
    private val factListAdapter: FactListAdapter by inject()

    private lateinit var uiEventObservable: Observable<FactListUiEvent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()
        setupUiEvents()
        subscribeToViewModel()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupAdapter() {
        rv_fact_list.adapter = factListAdapter
    }

    private fun setupUiEvents() {
        //TODO: figure out how to not use merge with only 1 event...
        uiEventObservable = Observable.merge<FactListUiEvent>(
            fab.clicks().map { FactListUiEvent.FloatingActionButtonClicked },
            fab.clicks().map { FactListUiEvent.FloatingActionButtonClicked })
            .startWith(FactListUiEvent.OnViewStart)
    }

    private fun subscribeToViewModel() {
        val disposable = uiEventObservable
            .compose(viewModel.viewState)
            .subscribeOn(Schedulers.io()) //TODO: maybe move this somewhere else
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                onViewStateReceived(it)
            }
        compositeDisposable.add(disposable)
    }

    private fun onViewStateReceived(viewState: ViewState) {
        when (viewState) {
            is FactListViewState.LoadingState -> renderLoadingState()
            is FactListViewState.ContentState -> renderContentState(viewState)
            is FactListViewState.ErrorState -> renderErrorState(viewState)
            is FactListViewState.NavigateToSuggestions -> navigateToSuggestions()
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

    private fun navigateToSuggestions() {
        navController.navigate(R.id.action_factListFragment_to_suggestionDialogFragment)
    }
}