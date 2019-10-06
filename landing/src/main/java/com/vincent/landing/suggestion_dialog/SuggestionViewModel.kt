package com.vincent.landing.suggestion_dialog

import com.vincent.core.ui.BaseViewModel
import com.vincent.core.utils.ResourceProvider
import com.vincent.core.utils.RxProvider
import com.vincent.domain.model.Suggestion
import com.vincent.domain.repository.SuggestionRepository
import com.vincent.landing.R
import com.vincent.landing.suggestion_dialog.validation.SuggestionValidator
import timber.log.Timber
import java.net.SocketTimeoutException

internal class SuggestionViewModel(
    rxProvider: RxProvider,
    navigator: SuggestionNavigator,
    private val resourceProvider: ResourceProvider,
    private val suggestionRepository: SuggestionRepository,
    private val suggestionValidator: SuggestionValidator
) : BaseViewModel<SuggestionViewState, SuggestionNavigator>(rxProvider, navigator) {

    private var viewState = SuggestionViewState()

    private var name = ""
    private var email = ""
    private var suggestion = ""

    fun onNameTextChanged(text: String) {
        name = text
    }

    fun onEmailTextChanged(text: String) {
        email = text

        viewState = viewState.copy(emailError = null, suggestionError = null)
        viewStateSubject.onNext(viewState)
    }

    fun onSuggestionTextChanged(text: String) {
        suggestion = text

        viewState = viewState.copy(emailError = null, suggestionError = null)
        viewStateSubject.onNext(viewState)
    }

    fun onCancelClicked() {
        viewState = viewState.copy(dismissed = true)
        viewStateSubject.onNext(viewState)
    }

    fun onSendClicked() {
        val emailError = suggestionValidator.validateEmail(email)
        val suggestionError = suggestionValidator.validateSuggestion(suggestion)

        if (emailError.isNotEmpty() || suggestionError.isNotEmpty()) {
            viewState = viewState.copy(emailError = emailError, suggestionError = suggestionError)
            viewStateSubject.onNext(viewState)
            return
        }

       sendSuggestion()
    }

    private fun sendSuggestion() {
        val suggestion = Suggestion(name, email, suggestion)
        val disposable = suggestionRepository.sendSuggestion(suggestion)
            .subscribeOn(rxProvider.ioScheduler())
            .observeOn(rxProvider.uiScheduler())
            .doOnSubscribe { loadingSubject.onNext(true) }
            .doFinally { loadingSubject.onNext(false) }
            .subscribe({ onSendSuggestionSuccess() }, { onSendSuggestionError(it) })
        addDisposable(disposable)
    }

    private fun onSendSuggestionSuccess() {
        viewState = viewState.copy(dismissed = true)
        viewStateSubject.onNext(viewState)
    }

    private fun onSendSuggestionError(throwable: Throwable) {
        Timber.e(throwable)

        val error = when (throwable) {
            is SocketTimeoutException -> {
                resourceProvider.getString(R.string.internet_connection_error)
            }
            else -> resourceProvider.getString(R.string.generic_error)
        }

        snackbarSubject.onNext(error)
    }
}