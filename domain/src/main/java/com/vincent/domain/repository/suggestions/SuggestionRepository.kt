package com.vincent.domain.repository.suggestions

import com.vincent.network.apis.SuggestionsApi
import com.vincent.network.models.Suggestion
import io.reactivex.Completable

class SuggestionRepository(private val suggestionsApi: SuggestionsApi) {

    fun addSuggestion(suggestion: Suggestion): Completable {
        return suggestionsApi.sendSuggestion(suggestion)
    }
}