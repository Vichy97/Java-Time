package com.vincent.landing.suggestion_dialog

import com.vincent.core.ui.BaseDialogFragment
import com.vincent.landing.R

import org.koin.androidx.viewmodel.ext.android.viewModel

class SuggestionDialogFragment
    : BaseDialogFragment(R.layout.dialog_suggestion, suggestionModule) {

    private val viewModel: SuggestionViewModel by viewModel()
}