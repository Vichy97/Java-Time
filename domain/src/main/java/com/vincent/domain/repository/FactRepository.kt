package com.vincent.domain.repository

import com.vincent.network.apis.FactsApi
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class FactRepository(private val factsApi: FactsApi) {

    private val transformer: ObservableTransformer<Action, Result> =
        ObservableTransformer { actions ->
            actions.flatMap {
                factsApi.getAllFacts()
                    .toObservable()
                    .map { Result.Success(it) as Result }
                    .onErrorReturn { Result.Error }
                    .startWith(Result.InProgress)
            }
        }

    fun bind(actions: Observable<Action>): Observable<Result> {
        return actions.compose(transformer)
    }
}