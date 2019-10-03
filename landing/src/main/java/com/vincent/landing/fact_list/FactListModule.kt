package com.vincent.landing.fact_list

import com.vincent.core.utils.RxProvider
import com.vincent.domain.repository.FactRepository

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val factListModule = module {

    viewModel<FactListViewModel> {
        FactListViewModel(get<RxProvider>(), get<FactListNavigator>(), get<FactRepository>())
    }

    factory<FactListNavigator> {
        FactListNavigator(get<RxProvider>())
    }

    factory<FactListAdapter> {
        FactListAdapter()
    }
}