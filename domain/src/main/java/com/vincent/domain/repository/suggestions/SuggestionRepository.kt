package com.vincent.domain.repository.suggestions

import com.vincent.core.utils.RxProvider
import com.vincent.domain.repository.base.Result
import com.vincent.network.apis.SuggestionsApi
import io.reactivex.Observable

import io.reactivex.ObservableTransformer

class SuggestionRepository(private val rxProvider: RxProvider,
                           private val suggestionsApi: SuggestionsApi) {

    val addSuggestion = ObservableTransformer<SuggestionAction.AddSuggestion, Result> { actions ->
        actions.flatMap {
            suggestionsApi.sendSuggestion(it.suggestion)
                .andThen(Observable.just(SuggestionResult.Success as Result))
                .onErrorReturn { throwable ->  Result.Error(throwable) }
                .subscribeOn(rxProvider.getIoScheduler())
                .observeOn(rxProvider.getUiScheduler())
                .startWith(Result.InProgress)
        }
    }
}