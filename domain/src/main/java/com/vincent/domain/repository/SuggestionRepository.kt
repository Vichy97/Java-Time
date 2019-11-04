package com.vincent.domain.repository

import com.vincent.domain.model.Suggestion
import com.vincent.network.api.SuggestionsApi
import com.vincent.network.model.SuggestionRequest

class SuggestionRepository(private val suggestionsApi: SuggestionsApi) {

    suspend fun sendSuggestion(suggestion: Suggestion) {
        val suggestionRequest = getSuggestionRequest(suggestion)

        suggestionsApi.sendSuggestion(suggestionRequest)
    }

    private fun getSuggestionRequest(suggestion: Suggestion): SuggestionRequest {
        return SuggestionRequest(suggestion.name, suggestion.email, suggestion.body)
    }
}