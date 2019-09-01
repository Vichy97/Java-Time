package com.vincent.landing.suggestion_dialog

import androidx.lifecycle.viewModelScope
import com.vincent.core.ui.BaseViewModel
import com.vincent.core.utils.ResourceProvider
import com.vincent.domain.repository.SuggestionRepository
import com.vincent.network.models.Suggestion

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SuggestionViewModel(
    resourceProvider: ResourceProvider,
    private val suggestionRepository: SuggestionRepository
) : BaseViewModel(resourceProvider) {

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
        submitSuggestion()
    }

    private fun submitSuggestion() {
        val suggestion = Suggestion(name, email, suggestion)
        viewModelScope.launch {
            try {
                suggestionRepository.addSuggestion(suggestion)
                viewStateChannel.send(SuggestionViewState.ContentState(true))
            } catch (e: Exception) {

            }
        }
    }
}