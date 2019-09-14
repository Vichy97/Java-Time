package com.vincent.landing.suggestion_dialog

import com.vincent.core.ui.BaseViewModel
import com.vincent.core.utils.ResourceProvider
import com.vincent.core.utils.RxProvider
import com.vincent.domain.repository.SuggestionRepository

class SuggestionViewModel(
    resourceProvider: ResourceProvider,
    rxProvider: RxProvider,
    navigator: SuggestionNavigator,
    private val suggestionRepository: SuggestionRepository
) : BaseViewModel<SuggestionNavigator>(resourceProvider, rxProvider, navigator) {

    private var email: String = ""
    private var name: String = ""
    private var suggestion: String = ""

    fun onEmailTextChanged(text: String) {

    }

    fun onBodyTextChanged(text: String) {

    }

    fun onNameTextChanged(text: String) {

    }

    fun onCancelClicked() {

    }

    fun onSendClicked() {
        if (suggestion.isBlank()) {
            TODO("send error state")
            return
        }
        if (email.isNotEmpty()) {
            TODO("validate email")
            return
        }
    }
}