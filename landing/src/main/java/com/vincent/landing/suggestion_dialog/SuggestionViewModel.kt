package com.vincent.landing.suggestion_dialog

import androidx.navigation.NavArgs

import com.vincent.core.analytics.AnalyticsService
import com.vincent.core.analytics.Page
import com.vincent.core.ui.BaseViewModel
import com.vincent.core.util.ResourceProvider
import com.vincent.domain.model.Suggestion
import com.vincent.domain.repository.SuggestionRepository
import com.vincent.landing.suggestion_dialog.validation.SuggestionValidator
import kotlinx.coroutines.CoroutineDispatcher

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

import kotlin.Exception
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
internal class SuggestionViewModel(
    resourceProvider: ResourceProvider,
    uiDispatcher: CoroutineDispatcher,
    ioDispatcher: CoroutineDispatcher,
    navigator: SuggestionNavigator,
    private val analyticsService: AnalyticsService,
    private val suggestionRepository: SuggestionRepository,
    private val suggestionValidator: SuggestionValidator
) : BaseViewModel<SuggestionViewState>(resourceProvider, uiDispatcher, ioDispatcher, navigator) {

    private var viewState = SuggestionViewState()

    private var name = ""
    private var email = ""
    private var suggestion = ""

    override fun start(arguments: NavArgs?) {
        super.start(arguments)

        analyticsService.trackPage(Page.SUGGESTION)
    }

    fun onNameTextChanged(text: String) {
        name = text
    }

    fun onEmailTextChanged(text: String) {
        email = text

        viewState = viewState.copy(emailError = null, suggestionError = null)
        sendViewState(viewState)
    }

    fun onSuggestionTextChanged(text: String) {
        suggestion = text

        viewState = viewState.copy(emailError = null, suggestionError = null)
        sendViewState(viewState)
    }

    fun onCancelClicked() {
        viewState = viewState.copy(dismissed = true)
        sendViewState(viewState)
    }

    fun onSendClicked() {
        val emailError = suggestionValidator.validateEmail(email)
        val suggestionError = suggestionValidator.validateSuggestion(suggestion)

        if (emailError.isNotEmpty() || suggestionError.isNotEmpty()) {
            viewState = viewState.copy(emailError = emailError, suggestionError = suggestionError)
            sendViewState(viewState)
            return
        }

        sendSuggestion()
    }

    private fun sendSuggestion() = ioScope.launch {
        try {
            val suggestion = Suggestion(name, email, suggestion)
            suggestionRepository.sendSuggestion(suggestion)
            onSendSuggestionSuccess()
        } catch (e: Exception) {
            Timber.e(e)
            onError(e)
        }
    }

    private fun onSendSuggestionSuccess() {
        viewState = viewState.copy(dismissed = true)
        sendViewState(viewState)
    }
}