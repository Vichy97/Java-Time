package com.vincent.domain.repository

import com.vincent.core.utils.RxProvider
import com.vincent.domain.model.Suggestion
import com.vincent.network.api.SuggestionsApi
import com.vincent.network.model.SuggestionRequest

import io.reactivex.Completable

class SuggestionRepository(
    private val rxProvider: RxProvider,
    private val suggestionsApi: SuggestionsApi
) {

    fun sendSuggestion(suggestion: Suggestion): Completable {
        val suggestionRequest = getSuggestionRequest(suggestion)

        return suggestionsApi.sendSuggestion(suggestionRequest)
            .subscribeOn(rxProvider.ioScheduler())
    }

    private fun getSuggestionRequest(suggestion: Suggestion): SuggestionRequest {
        return SuggestionRequest(suggestion.name, suggestion.email, suggestion.body)
    }
}