package com.vincent.domain.repository.suggestions

import com.vincent.domain.repository.base.Action
import com.vincent.network.models.Suggestion

sealed class SuggestionAction : Action() {

    data class AddSuggestion(val suggestion: Suggestion) : SuggestionAction()
}