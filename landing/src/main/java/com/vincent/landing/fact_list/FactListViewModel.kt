package com.vincent.landing.fact_list

import android.os.Bundle
import com.vincent.core.ui.BaseViewModel
import com.vincent.core.utils.ResourceProvider
import com.vincent.core.utils.RxProvider
import com.vincent.domain.repository.Action
import com.vincent.domain.repository.FactRepository
import com.vincent.domain.repository.Result
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

internal class FactListViewModel(
    resourceProvider: ResourceProvider,
    rxProvider: RxProvider,
    factListNavigator: FactListNavigator,
    private val factsRepository: FactRepository
) : BaseViewModel<FactListNavigator>(resourceProvider, rxProvider, factListNavigator) {

    private lateinit var viewStateObservable: Observable<FactListViewState>
    private val initialState: FactListViewState = FactListViewState.LoadingState

    override fun start(args: Bundle?) {

    }

    fun bind(uiEvents: Observable<FactListUiEvent>): Observable<FactListViewState> {
        return factsRepository.bind( uiEvents.map { Action() })
            .scan(initialState) { state: FactListViewState, result: Result ->
                when (result) {
                    is Result.Success -> FactListViewState.ContentState(result.facts)
                    is Result.Error -> FactListViewState.ErrorState("error")
                    is Result.InProgress -> FactListViewState.LoadingState
                }
            }
    }

    fun unBind() {

    }
}