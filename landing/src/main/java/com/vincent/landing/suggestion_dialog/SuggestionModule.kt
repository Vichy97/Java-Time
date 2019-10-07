package com.vincent.landing.suggestion_dialog

import com.vincent.core.analytics.AnalyticsService
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
            get<SuggestionNavigator>(),
            get<ResourceProvider>(),
            get<AnalyticsService>(),
            get<SuggestionRepository>(),
            get<SuggestionValidator>()
        )
    }

    factory<SuggestionValidator> {
        SuggestionValidator(get<ResourceProvider>())
    }

    factory<SuggestionNavigator> {
        SuggestionNavigator(get<RxProvider>())
    }
}