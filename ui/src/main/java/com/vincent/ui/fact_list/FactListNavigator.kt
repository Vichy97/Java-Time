package com.vincent.ui.fact_list

import com.vincent.core.ui.BaseNavigator
import com.vincent.core.utils.RxProvider
import com.vincent.ui.R

internal class FactListNavigator(rxProvider: RxProvider) : BaseNavigator(rxProvider) {

    fun showSuggestionDialog() {
        navigate(R.id.action_factListFragment_to_suggestionDialogFragment)
    }

    fun navigateToAbout() {
        navigate(R.id.action_factListFragment_to_aboutFragment)
    }
}