package com.vincent.landing.suggestion_dialog

import com.vincent.core.ui.BaseDialogFragment
import com.vincent.core.ui.BaseViewState
import com.vincent.landing.R

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

import org.koin.androidx.viewmodel.ext.android.viewModel

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class SuggestionDialogFragment : BaseDialogFragment(R.layout.fragment_suggestion, suggestionModule) {

    val viewModel: SuggestionViewModel by viewModel()

    override fun onViewStateReceived(viewState: BaseViewState) {
        when (viewState) {
            is SuggestionViewState.LoadingState -> renderLoadingState()
            is SuggestionViewState.ContentState -> renderContentState(viewState)
            is SuggestionViewState.ErrorState -> renderErrorState(viewState)
        }
    }

    private fun renderLoadingState() {

    }

    private fun renderContentState(viewState: SuggestionViewState.ContentState) {

    }

    private fun renderErrorState(viewState: SuggestionViewState.ErrorState) {

    }
}