package com.vincent.landing.suggestion_dialog

import com.vincent.core.ui.BaseViewModel
import com.vincent.core.ui.BaseViewState
import com.vincent.core.utils.RxProvider
import com.vincent.domain.repository.SuggestionRepository
import com.vincent.landing.suggestion_dialog.validation.SuggestionValidator

internal class SuggestionViewModel(
    rxProvider: RxProvider,
    private val suggestionRepository: SuggestionRepository,
    private val suggestionValidator: SuggestionValidator,
    navigator: SuggestionNavigator
) : BaseViewModel<BaseViewState, SuggestionNavigator>(rxProvider, navigator) {

}