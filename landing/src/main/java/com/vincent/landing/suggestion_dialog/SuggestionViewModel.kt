package com.vincent.landing.suggestion_dialog

import androidx.navigation.NavArgs

import com.vincent.core.analytics.AnalyticsService
import com.vincent.core.analytics.Page
import com.vincent.core.ui.BaseViewModel
import com.vincent.core.utils.ResourceProvider
import com.vincent.core.utils.RxProvider
import com.vincent.domain.model.Suggestion
import com.vincent.domain.repository.SuggestionRepository
import com.vincent.landing.suggestion_dialog.validation.SuggestionValidator

internal class SuggestionViewModel(
    rxProvider: RxProvider,
    navigator: SuggestionNavigator,
    resourceProvider: ResourceProvider,
    private val analyticsService: AnalyticsService,
    private val suggestionRepository: SuggestionRepository,
    private val suggestionValidator: SuggestionValidator
) : BaseViewModel<SuggestionViewState>(rxProvider, resourceProvider, navigator) {

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

    private fun sendSuggestion() {
        val suggestion = Suggestion(name, email, suggestion)
        val disposable = suggestionRepository.sendSuggestion(suggestion)
            .doOnSubscribe { showLoading(true) }
            .doFinally { showLoading(false) }
            .subscribe({ onSendSuggestionSuccess() }, { onError(it) })
        addDisposables(disposable)
    }

    private fun onSendSuggestionSuccess() {
        viewState = viewState.copy(dismissed = true)
        sendViewState(viewState)
    }
}