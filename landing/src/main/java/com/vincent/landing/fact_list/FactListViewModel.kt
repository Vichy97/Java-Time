package com.vincent.landing.fact_list

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.vincent.core.ui.BaseViewModel
import com.vincent.core.ui.NavigationEvent
import com.vincent.core.utils.ResourceProvider
import com.vincent.domain.repository.FactRepository
import com.vincent.landing.R
import com.vincent.network.models.Fact
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
internal class FactListViewModel(
    resourceProvider: ResourceProvider,
    private val factsApi: FactRepository
) : BaseViewModel(resourceProvider) {

    override fun start(args: Bundle?) {
        getFacts()
    }

    private fun getFacts() {
        viewModelScope.launch {
            viewStateChannel.send(FactListViewState.LoadingState)
            try {
                val facts = factsApi.getFacts()
                onGetFactsSuccess(facts)
            } catch (e: Exception) {
                onGetFactsError(e)
            }
        }
    }

    private suspend fun onGetFactsSuccess(facts: List<Fact>) {
        viewStateChannel.send(FactListViewState.ContentState(facts))
    }

    private suspend fun onGetFactsError(e: Exception) {
        viewStateChannel.send(FactListViewState.ErrorState(e.message ?: "error fetching fact"))
    }

    fun onFloatingActionButtonClicked() {
        viewModelScope.launch {
            val navigationEvent = NavigationEvent.IdEvent(
                R.id.action_factListFragment_to_suggestionDialogFragment,
                null
            )
            navigationChannel.send(navigationEvent)
        }
    }
}