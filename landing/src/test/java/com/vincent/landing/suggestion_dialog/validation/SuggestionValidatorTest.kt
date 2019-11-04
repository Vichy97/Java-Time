package com.vincent.landing.suggestion_dialog.validation

import com.vincent.core.util.ResourceProvider
import com.vincent.core_test.BaseTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Assert.*
import org.junit.Test

@ExperimentalCoroutinesApi
class SuggestionValidatorTest : BaseTest() {

    private val resourceProvider = mockk<ResourceProvider>(relaxed = true)
    private lateinit var validator: SuggestionValidator

    override fun setup() {
        super.setup()

        validator = SuggestionValidator(resourceProvider)
    }

    @Test
    fun should_returnEmptyString_when_validateEmailWithValidEmail() {
        val email = "valid.email@test.com"

        val error = validator.validateEmail(email)
        assertEquals("", error)
    }

    @Test
    fun should_returnError_when_validateEmailWithInvalidEmail() {
        val email = "invalidEmail@"
        val emailError = "invalid email"
        every { resourceProvider.getString(any()) } returns emailError

        val error = validator.validateEmail(email)
        assertEquals(emailError, error)
    }

    @Test
    fun should_returnEmptyString_when_validateEmailWithEmptyEmail() {
        val error = validator.validateEmail("")
        assertEquals("", error)
    }

    @Test
    fun should_returnError_when_validateSuggestionWithBlankSuggestion() {
        val suggestionError = "invalid suggestion"
        every { resourceProvider.getString(any()) } returns suggestionError

        val error = validator.validateSuggestion(" ")
        assertEquals(suggestionError, error)
    }

    @Test
    fun should_returnEmptyString_when_validateSuggestionWithNonEmptySuggestion() {
        val error = validator.validateSuggestion("some suggestion")
        assertEquals("", error)
    }
}