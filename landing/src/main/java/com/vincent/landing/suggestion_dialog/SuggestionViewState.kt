package com.vincent.landing.suggestion_dialog

import com.vincent.core.ui.BaseViewState

internal data class SuggestionViewState(
    val dismissed: Boolean = false,
    val emailError: String? = null,
    val suggestionError: String? = null
): BaseViewState()