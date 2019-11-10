package com.vincent.ui.fact_list

import com.vincent.core.analytics.AnalyticsService
import com.vincent.core.utils.ResourceProvider
import com.vincent.core.utils.RxProvider
import com.vincent.domain.repository.FactRepository

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val factListModule = module {

    viewModel<FactListViewModel> {
        FactListViewModel(
            get<RxProvider>(),
            get<ResourceProvider>(),
            get<FactListNavigator>(),
            get<AnalyticsService>(),
            get<FactRepository>()
        )
    }

    factory<FactListNavigator> {
        FactListNavigator(get<RxProvider>())
    }

    factory<FactListAdapter> {
        FactListAdapter()
    }
}