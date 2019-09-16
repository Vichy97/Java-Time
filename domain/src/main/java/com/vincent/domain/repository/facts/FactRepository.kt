package com.vincent.domain.repository.facts

import com.vincent.core.utils.RxProvider
import com.vincent.domain.repository.base.Result
import com.vincent.network.apis.FactsApi
import io.reactivex.ObservableTransformer

class FactRepository(private val rxProvider: RxProvider,
                     private val factsApi: FactsApi) {

    val getAllFacts = ObservableTransformer<FactAction.GetAllFacts, Result> { actions ->
        actions.flatMap {
            factsApi.getAllFacts()
                .toObservable()
                .map { FactResult.Success(it) as Result }
                .onErrorReturn { Result.Error(it) }
                .subscribeOn(rxProvider.getIoScheduler())
                .observeOn(rxProvider.getUiScheduler())
                .startWith(Result.InProgress)
        }
    }
}