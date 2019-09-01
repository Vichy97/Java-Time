package com.vincent.landing.suggestion_dialog

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val suggestionModule = module {

    viewModel {
        SuggestionViewModel(get(), get())
    }
}