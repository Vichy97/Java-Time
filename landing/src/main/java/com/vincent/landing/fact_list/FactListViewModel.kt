package com.vincent.landing.fact_list

import androidx.navigation.NavArgs

import com.vincent.core.analytics.AnalyticsService
import com.vincent.core.analytics.Page
import com.vincent.core.ui.BaseViewModel
import com.vincent.core.util.ResourceProvider
import com.vincent.domain.model.Fact
import com.vincent.domain.repository.FactRepository
import kotlinx.coroutines.CoroutineDispatcher

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
internal class FactListViewModel(
    resourceProvider: ResourceProvider,
    uiDispatcher: CoroutineDispatcher,
    ioDispatcher: CoroutineDispatcher,
    private val navigator: FactListNavigator,
    private val analyticsService: AnalyticsService,
    private val factsRepository: FactRepository
) : BaseViewModel<FactListViewState>(resourceProvider, uiDispatcher, ioDispatcher, navigator) {

    override fun start(arguments: NavArgs?) {
        getFacts()

        analyticsService.trackPage(Page.FACT_LIST)
    }

    private fun getFacts() = ioScope.launch {
        try {
            val facts = factsRepository.getAllFacts()
            onGetFactsSuccess(facts)
        } catch (e: Exception) {
            Timber.e(e)
            onError(e)
        }
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