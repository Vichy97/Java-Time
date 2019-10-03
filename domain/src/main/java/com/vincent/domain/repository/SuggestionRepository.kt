package com.vincent.domain.repository

import com.vincent.domain.model.Suggestion
import com.vincent.network.apis.SuggestionsApi
import com.vincent.network.models.SuggestionRequest

import io.reactivex.Completable

class SuggestionRepository(
    private val suggestionsApi: SuggestionsApi
) {

    fun sendSuggestion(suggestion: Suggestion): Completable {
        val suggestionRequest = getSuggestionRequest(suggestion)

        return suggestionsApi.sendSuggestion(suggestionRequest)
    }

    private fun getSuggestionRequest(suggestion: Suggestion): SuggestionRequest {
        return SuggestionRequest(suggestion.name, suggestion.email, suggestion.body)
    }
}