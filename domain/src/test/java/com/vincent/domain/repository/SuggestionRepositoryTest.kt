package com.vincent.domain.repository

import com.vincent.core_test.BaseTest
import com.vincent.domain.model.Suggestion
import com.vincent.network.api.SuggestionsApi
import com.vincent.network.model.SuggestionRequest
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Test

class SuggestionRepositoryTest : BaseTest() {

    private val suggestionsApi = mockk<SuggestionsApi>(relaxed = true)
    private lateinit var suggestionRepository: SuggestionRepository
    private val suggestion = Suggestion("name", "email", "body")

    override fun setup() {
        super.setup()

        suggestionRepository = SuggestionRepository(suggestionsApi)
    }

    @Test
    fun should_makeApiCall_when_sendingSuggestion() {
        val slot = slot<SuggestionRequest>()

        suggestionRepository.sendSuggestion(suggestion)

        verify { suggestionsApi.sendSuggestion(suggestionRequest = capture(slot)) }
        assertEquals(suggestion.name, slot.captured.name)
        assertEquals(suggestion.email, slot.captured.email)
        assertEquals(suggestion.body, slot.captured.body)
    }

    @Test
    fun should_emitError_when_suggestionsApiReturnsError() {
        val exception = Exception("test")
        every { suggestionsApi.sendSuggestion(any()) } returns Completable.error(exception)

        suggestionRepository.sendSuggestion(suggestion)
            .test()
            .assertError(exception)
    }

    @Test
    fun should_completeSuccessfully_when_suggestionsApiReturnsSuccess() {
        every { suggestionsApi.sendSuggestion(any()) } returns Completable.complete()

        suggestionRepository.sendSuggestion(suggestion)
            .test()
            .assertComplete()
    }
}