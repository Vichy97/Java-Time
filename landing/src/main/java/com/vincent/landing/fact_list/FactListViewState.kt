package com.vincent.landing.fact_list

import com.vincent.core.ui.BaseViewState
import com.vincent.network.models.Fact

internal sealed class FactListViewState : BaseViewState() {

    object LoadingState : BaseViewState.LoadingState()
    data class ContentState(val facts: List<Fact>) : BaseViewState.ContentState()
    data class ErrorState(val message: String) : BaseViewState.ErrorState()
}