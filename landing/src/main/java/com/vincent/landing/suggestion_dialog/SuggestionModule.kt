package com.vincent.landing.suggestion_dialog

import com.vincent.core.analytics.AnalyticsService
import com.vincent.core.util.IO_DISPATCHER
import com.vincent.core.util.ResourceProvider
import com.vincent.core.util.UI_DISPATCHER
import com.vincent.domain.repository.SuggestionRepository
import com.vincent.landing.suggestion_dialog.validation.SuggestionValidator

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
internal val suggestionModule = module {

    viewModel<SuggestionViewModel> {
        SuggestionViewModel(
            get<ResourceProvider>(),
            get<CoroutineDispatcher>(named(UI_DISPATCHER)),
            get<CoroutineDispatcher>(named(IO_DISPATCHER)),
            get<SuggestionNavigator>(),
            get<AnalyticsService>(),
            get<SuggestionRepository>(),
            get<SuggestionValidator>()
        )
    }

    factory<SuggestionValidator> {
        SuggestionValidator(get<ResourceProvider>())
    }

    factory<SuggestionNavigator> {
        SuggestionNavigator(get(named(UI_DISPATCHER)))
    }
}