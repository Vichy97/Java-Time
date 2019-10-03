package com.vincent.landing.suggestion_dialog.validation

import android.util.Patterns

import com.vincent.core.utils.ResourceProvider
import com.vincent.landing.R

internal class SuggestionValidator(private val resourceProvider: ResourceProvider) {

    private fun validateEmail(email: String): String {
        if (email.isEmpty()) {
            return ""
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return resourceProvider.getString(R.string.error_invalid_email)
        }
        return ""
    }

    private fun validateSuggestion(suggestion: String): String {
        if (suggestion.isBlank()) {
            return resourceProvider.getString(R.string.dialog_suggestion_suggestion_empty_error)
        }
        return ""
    }
}