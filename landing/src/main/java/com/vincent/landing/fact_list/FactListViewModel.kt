package com.vincent.landing.fact_list

import com.vincent.core.ui.BaseViewModel
import com.vincent.core.utils.RxProvider
import com.vincent.domain.repository.FactRepository

internal class FactListViewModel(
    rxProvider: RxProvider,
    navigator: FactListNavigator,
    private val factsRepository: FactRepository
) : BaseViewModel<FactListViewState, FactListNavigator>(rxProvider, navigator) {

}