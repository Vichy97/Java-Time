package com.vincent.landing.suggestion_dialog

import com.vincent.core.ui.BaseViewState

internal class SuggestionViewState : BaseViewState() {

    object LoadingState : BaseViewState.LoadingState()
    data class ContentState(val completed: Boolean = false) : BaseViewState.ContentState()
    data class ErrorState(
        val emailError: String,
        val suggestionError: String,
        val message: String = ""
    ) : BaseViewState.ErrorState()
}