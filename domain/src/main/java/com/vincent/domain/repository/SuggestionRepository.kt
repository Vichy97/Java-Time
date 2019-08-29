package com.vincent.domain.repository

import com.vincent.network.apis.SuggestionsApi
import com.vincent.network.models.Suggestion

class SuggestionRepository(private val suggestionsApi: SuggestionsApi) {

    suspend fun addSuggestion(suggestion: Suggestion) {
        suggestionsApi.sendSuggestion(suggestion)
    }
}