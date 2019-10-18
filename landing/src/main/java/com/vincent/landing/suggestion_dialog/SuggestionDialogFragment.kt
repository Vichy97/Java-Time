package com.vincent.landing.suggestion_dialog

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.vincent.core.ui.BaseDialogFragment
import com.vincent.landing.R
import io.reactivex.android.schedulers.AndroidSchedulers

import kotlinx.android.synthetic.main.dialog_suggestion.*

import org.koin.androidx.viewmodel.ext.android.viewModel

class SuggestionDialogFragment
    : BaseDialogFragment(R.layout.dialog_suggestion, suggestionModule) {

    private val viewModel: SuggestionViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewEvents()
    }

    private fun setupViewEvents() {
        et_name.doAfterTextChanged {
            viewModel.onNameTextChanged(it.toString())
        }
        et_email.doAfterTextChanged {
            viewModel.onEmailTextChanged(it.toString())
        }
        et_suggestion.doAfterTextChanged {
            viewModel.onSuggestionTextChanged(it.toString())
        }
        btn_cancel.setOnClickListener { viewModel.onCancelClicked() }
        btn_send.setOnClickListener { viewModel.onSendClicked() }
    }

    override fun onStart() {
        super.onStart()

        subscribeToViewModel()
        viewModel.start()
    }

    private fun subscribeToViewModel() {
        val viewStateDisposable = viewModel.viewStateEvents
            .observeOn(rxProvider.uiScheduler())
            .subscribe { onViewStateEvent(it) }
        val snackbarDisposable = viewModel.snackbarEvents
            .observeOn(rxProvider.uiScheduler())
            .subscribe { showSnackbar(it) }
        val loadingDisposable = viewModel.loadingEvents
            .observeOn(rxProvider.uiScheduler())
            .subscribe { showLoading(it) }

        addDisposables(viewStateDisposable, snackbarDisposable, loadingDisposable)
    }

    private fun onViewStateEvent(viewState: SuggestionViewState) {
        if (viewState.dismissed) {
            return dismiss()
        }

        til_email.error = viewState.emailError
        til_suggestion.error = viewState.suggestionError
    }

    private fun showLoading(loading: Boolean) {
        progress_bar.isVisible = loading
        view?.isClickable = !loading
    }
}