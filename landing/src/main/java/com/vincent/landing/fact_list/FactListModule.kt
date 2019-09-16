package com.vincent.landing.fact_list

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val factListModule = module {

    viewModel {
        FactListViewModel(get(), get(), get())
    }

    factory {
        FactListAdapter()
    }
}