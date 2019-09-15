package com.vincent.landing.suggestion_dialog

import com.vincent.core.ui.ViewState

internal class SuggestionViewState : ViewState() {

    object LoadingState : ViewState.LoadingState()
    data class ContentState(val completed: Boolean = false) : ViewState.ContentState()
    data class ErrorState(
        val emailError: String,
        val suggestionError: String,
        val message: String = ""
    ) : ViewState.ErrorState()
}