package com.vincent.landing.fact_list

import com.vincent.core.analytics.AnalyticsService
import com.vincent.core.util.IO_DISPATCHER
import com.vincent.core.util.ResourceProvider
import com.vincent.core.util.UI_DISPATCHER
import com.vincent.domain.repository.FactRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
internal val factListModule = module {

    viewModel<FactListViewModel> {
        FactListViewModel(
            get<ResourceProvider>(),
            get<CoroutineDispatcher>(named(UI_DISPATCHER)),
            get<CoroutineDispatcher>(named(IO_DISPATCHER)),
            get<FactListNavigator>(),
            get<AnalyticsService>(),
            get<FactRepository>()
        )
    }

    factory<FactListNavigator> {
        FactListNavigator(get<CoroutineDispatcher>(named(UI_DISPATCHER)))
    }

    factory<FactListAdapter> {
        FactListAdapter()
    }
}