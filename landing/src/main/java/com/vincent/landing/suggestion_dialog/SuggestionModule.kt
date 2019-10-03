package com.vincent.landing.suggestion_dialog

import com.vincent.core.utils.ResourceProvider
import com.vincent.core.utils.RxProvider
import com.vincent.domain.repository.SuggestionRepository
import com.vincent.landing.suggestion_dialog.validation.SuggestionValidator

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val suggestionModule = module {

    viewModel<SuggestionViewModel> {
        SuggestionViewModel(
            get<RxProvider>(),
            get<SuggestionRepository>(),
            get<SuggestionValidator>(),
            get<SuggestionNavigator>()
        )
    }

    factory<SuggestionValidator> {
        SuggestionValidator(get<ResourceProvider>())
    }

    factory<SuggestionNavigator> {
        SuggestionNavigator(get<RxProvider>())
    }
}