package com.vincent.ui.suggestion_dialog.validation

import androidx.core.util.PatternsCompat

import com.vincent.core.utils.ResourceProvider
import com.vincent.ui.R

internal class SuggestionValidator(private val resourceProvider: ResourceProvider) {

    fun validateEmail(email: String): String {
        if (email.isEmpty()) {
            return ""
        }
        if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            return resourceProvider.getString(R.string.error_invalid_email)
        }
        return ""
    }

    fun validateSuggestion(suggestion: String): String {
        if (suggestion.isBlank()) {
            return resourceProvider.getString(R.string.dialog_suggestion_suggestion_empty_error)
        }
        return ""
    }
}