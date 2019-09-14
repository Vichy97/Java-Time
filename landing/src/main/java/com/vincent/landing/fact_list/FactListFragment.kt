package com.vincent.landing.fact_list

import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.jakewharton.rxbinding3.view.clicks

import com.vincent.core.ui.BaseFragment
import com.vincent.core.ui.BaseViewState
import com.vincent.landing.R
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject

import kotlinx.android.synthetic.main.fragment_fact_list.*

internal class FactListFragment : BaseFragment(R.layout.fragment_fact_list, factListModule) {

    private val viewModel: FactListViewModel by viewModel()
    private val factListAdapter: FactListAdapter by inject()

    private lateinit var viewStateObservable: Observable<FactListViewState>
    private lateinit var uiEventObservable: Observable<FactListUiEvent>

    private val compositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()

        uiEventObservable = Observable.merge<FactListUiEvent>(
            fab.clicks().map {FactListUiEvent.FloatingActionButtonClickedEvent},
            fab.clicks().map {FactListUiEvent.FloatingActionButtonClickedEvent}
        ).startWith(FactListUiEvent.OnViewStart)

        viewStateObservable = viewModel.bind(uiEventObservable)

        compositeDisposable.add(viewStateObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    is FactListViewState.LoadingState -> {

                    }
                    is FactListViewState.ContentState -> {

                    }
                    is FactListViewState.ErrorState -> {

                    }
                }
            }
        )

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