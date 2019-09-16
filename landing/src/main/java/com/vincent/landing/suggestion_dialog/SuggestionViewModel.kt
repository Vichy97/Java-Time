package com.vincent.landing.suggestion_dialog

import com.vincent.core.ui.BaseViewModel
import com.vincent.core.utils.ResourceProvider
import com.vincent.core.utils.RxProvider
import com.vincent.domain.repository.suggestions.SuggestionRepository

class SuggestionViewModel(
    resourceProvider: ResourceProvider,
    rxProvider: RxProvider,
    private val suggestionRepository: SuggestionRepository
) : BaseViewModel(resourceProvider, rxProvider) {

    private var email: String = ""
    private var name: String = ""
    private var suggestion: String = ""
}