package com.vincent.landing

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val landingModule = module {

    viewModel {
        LandingViewModel(get())
    }
}