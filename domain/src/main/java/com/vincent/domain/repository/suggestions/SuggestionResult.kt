package com.vincent.domain.repository.suggestions

import com.vincent.domain.repository.base.Result

sealed class SuggestionResult : Result() {

    object Success : SuggestionResult()
}