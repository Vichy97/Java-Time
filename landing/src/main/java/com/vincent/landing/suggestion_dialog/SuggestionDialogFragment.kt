package com.vincent.landing.suggestion_dialog

import com.vincent.core.ui.BaseDialogFragment
import com.vincent.core.ui.ViewState
import com.vincent.landing.R

import org.koin.androidx.viewmodel.ext.android.viewModel

class SuggestionDialogFragment : BaseDialogFragment(R.layout.fragment_suggestion, suggestionModule) {

    val viewModel: SuggestionViewModel by viewModel()

    private fun onViewStateReceived(viewState: ViewState) {
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