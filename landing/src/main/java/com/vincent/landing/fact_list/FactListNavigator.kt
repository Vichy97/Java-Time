package com.vincent.landing.fact_list

import com.vincent.core.ui.BaseNavigator
import com.vincent.core.ui.NavigationEvent
import com.vincent.core.utils.RxProvider
import com.vincent.landing.R

internal class FactListNavigator(rxProvider: RxProvider) : BaseNavigator(rxProvider) {

    fun showSuggestionDialog() {
        val navigationEvent = NavigationEvent.IdEvent(
            R.id.action_factListFragment_to_suggestionDialogFragment,
            null
        )
        navigationSubject.onNext(navigationEvent)
    }
}