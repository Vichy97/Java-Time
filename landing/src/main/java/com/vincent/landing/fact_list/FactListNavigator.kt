package com.vincent.landing.fact_list

import com.vincent.core.ui.BaseNavigator
import com.vincent.landing.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
internal class FactListNavigator(dispatcher: CoroutineDispatcher) : BaseNavigator(dispatcher) {

    fun showSuggestionDialog() {
        navigate(R.id.action_factListFragment_to_suggestionDialogFragment)
    }

    fun navigateToAbout() {
        navigate(R.id.action_factListFragment_to_aboutFragment)
    }
}