package com.vincent.domain.repository.facts

import com.vincent.domain.repository.base.Action
import com.vincent.domain.repository.base.Result
import com.vincent.network.apis.FactsApi
import io.reactivex.ObservableTransformer

class FactRepository(private val factsApi: FactsApi) {

    val getAllFacts = ObservableTransformer<Action, Result> { actions ->
        actions.flatMap {
            factsApi.getAllFacts()
                .toObservable()
                .map { FactResult.Success(it) as Result }
                .onErrorReturn { Result.Error(it) }
                .startWith(Result.InProgress)
        }
    }
}