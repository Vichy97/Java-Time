package com.vincent.landing.fact_list

import com.vincent.core.ui.BaseViewModel
import com.vincent.core.utils.ResourceProvider
import com.vincent.core.utils.RxProvider
import com.vincent.domain.repository.facts.FactRepository
import com.vincent.domain.repository.facts.FactResult
import io.reactivex.Observable
import com.vincent.domain.repository.base.Result
import com.vincent.domain.repository.facts.FactAction
import io.reactivex.ObservableTransformer

internal class FactListViewModel(
    resourceProvider: ResourceProvider,
    rxProvider: RxProvider,
    private val factsRepository: FactRepository
) : BaseViewModel(resourceProvider, rxProvider) {

    private val initialViewState: FactListViewState = FactListViewState.LoadingState

    val viewState = ObservableTransformer<FactListUiEvent, FactListViewState> { uiEvents ->
        uiEvents.publish {
            Observable.merge<FactListViewState>(
                it.ofType(FactListUiEvent.FloatingActionButtonClicked::class.java)
                    .compose(floatingActionButtonClicked),
                it.ofType(FactListUiEvent.OnViewStart::class.java)
                    .compose(onViewStart)
            )
        }
    }

    private val floatingActionButtonClicked =
        ObservableTransformer<FactListUiEvent.FloatingActionButtonClicked, FactListViewState> {
            it.map { FactListViewState.NavigateToSuggestions }
        }

    private val onViewStart =
        ObservableTransformer<FactListUiEvent.OnViewStart, FactListViewState> {
            it.map { FactAction.GetAllFacts }
                .compose(factsRepository.getAllFacts)
                .compose(reducer)
        }

    private val reducer = ObservableTransformer<Result, FactListViewState> {
        it.scan(initialViewState) { state: FactListViewState, result: Result ->
            when (result) {
                is FactResult.Success -> FactListViewState.ContentState(result.facts)
                is Result.Error -> FactListViewState.ErrorState(result.e.message ?: "")
                is Result.InProgress -> FactListViewState.LoadingState
                else -> state
            }
        }
    }
}