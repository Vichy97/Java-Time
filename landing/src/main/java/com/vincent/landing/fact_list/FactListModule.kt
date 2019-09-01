package com.vincent.landing.fact_list

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
internal val factListModule = module {

    viewModel {
        FactListViewModel(get(), get())
    }

    factory {
        FactListAdapter()
    }
}