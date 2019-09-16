package com.vincent.landing.suggestion_dialog

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val suggestionModule = module {

    viewModel {
        SuggestionViewModel(get(), get(), get())
    }
}