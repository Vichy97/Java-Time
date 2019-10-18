package com.vincent.landing.fact_list

import android.os.Bundle
import com.vincent.core.analytics.AnalyticsService
import com.vincent.core.analytics.Page

import com.vincent.core.ui.BaseViewModel
import com.vincent.core.utils.ResourceProvider
import com.vincent.core.utils.RxProvider
import com.vincent.domain.model.Fact
import com.vincent.domain.repository.FactRepository
import com.vincent.landing.R

import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal class FactListViewModel(
    rxProvider: RxProvider,
    navigator: FactListNavigator,
    private val resourceProvider: ResourceProvider,
    private val analyticsService: AnalyticsService,
    private val factsRepository: FactRepository
) : BaseViewModel<FactListViewState, FactListNavigator>(rxProvider, navigator) {

    override fun start(arguments: Bundle?) {
        getFacts()

        analyticsService.trackPage(Page.FACT_LIST)
    }

    private fun getFacts() {
        val disposable = factsRepository.getAllFacts()
            .doOnSubscribe { showLoading(true) }
            .doFinally { showLoading(false) }
            .subscribe({ onGetFactsSuccess(it) }, { onGetFactsError(it) })
        addDisposable(disposable)
    }

    private fun showLoading(loading: Boolean) {
        loadingSubject.onNext(loading)
    }

    private fun onGetFactsSuccess(facts: List<Fact>) {
        val viewState = FactListViewState(facts)
        viewStateSubject.onNext(viewState)
    }

    private fun onGetFactsError(throwable: Throwable) {
        Timber.e(throwable)

        val error = when (throwable) {
            is SocketTimeoutException, is UnknownHostException -> {
                resourceProvider.getString(R.string.internet_connection_error)
            }
            else -> resourceProvider.getString(R.string.generic_error)
        }

        snackbarSubject.onNext(error)
    }

    fun onSwipeToRefresh() {

        getFacts()
    }

    fun onFloatingActionButtonClicked() {
        navigator.showSuggestionDialog()
    }

    fun onAboutClicked() {
        navigator.navigateToAbout()
    }
}