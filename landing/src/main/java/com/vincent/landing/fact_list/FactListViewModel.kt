package com.vincent.landing.fact_list

import androidx.navigation.NavArgs
import com.vincent.core.analytics.AnalyticsService
import com.vincent.core.analytics.Page

import com.vincent.core.ui.BaseViewModel
import com.vincent.core.utils.ResourceProvider
import com.vincent.core.utils.RxProvider
import com.vincent.domain.model.Fact
import com.vincent.domain.repository.FactRepository

internal class FactListViewModel(
    rxProvider: RxProvider,
    resourceProvider: ResourceProvider,
    private val navigator: FactListNavigator,
    private val analyticsService: AnalyticsService,
    private val factsRepository: FactRepository
) : BaseViewModel<FactListViewState>(rxProvider, resourceProvider, navigator) {

    override fun start(arguments: NavArgs?) {
        getFacts()

        analyticsService.trackPage(Page.FACT_LIST)
    }

    private fun getFacts() {
        val disposable = factsRepository.getAllFacts()
            .doOnSubscribe { showLoading(true) }
            .doFinally { showLoading(false) }
            .subscribe({ onGetFactsSuccess(it) }, { onError(it) })
        addDisposables(disposable)
    }

    private fun onGetFactsSuccess(facts: List<Fact>) {
        val viewState = FactListViewState(facts)
        sendViewState(viewState)
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