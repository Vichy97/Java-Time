package com.vincent.landing.fact_list

import com.vincent.core.ui.ViewState
import com.vincent.network.models.Fact

internal sealed class FactListViewState : ViewState() {

    object LoadingState : FactListViewState()
    data class ContentState(val facts: List<Fact>) : FactListViewState()
    data class ErrorState(val message: String) : FactListViewState()

    object NavigateToSuggestions : FactListViewState()
}