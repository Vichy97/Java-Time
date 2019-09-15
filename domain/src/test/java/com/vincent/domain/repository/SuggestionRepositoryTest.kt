package com.vincent.domain.repository

import com.vincent.domain.repository.suggestions.SuggestionRepository
import com.vincent.network.apis.SuggestionsApi
import com.vincent.network.models.Suggestion
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SuggestionRepositoryTest {

    private val suggestionsApi = mockk<SuggestionsApi>(relaxed = true)
    private lateinit var suggestionsRepository: SuggestionRepository

    @Before
    fun setup() {
        suggestionsRepository =
            SuggestionRepository(suggestionsApi)
    }

    @Test
    fun should_makeApiCall_when_addingSuggestion() {
        val suggestion = Suggestion("name", "email", "body")

        runBlocking { suggestionsRepository.addSuggestion(suggestion) }

        coVerify { suggestionsApi.sendSuggestion(suggestion) }
    }

    @Test(expected = Exception::class)
    fun should_throwException_when_apiCallFails() {
        val suggestion = Suggestion("name", "email", "body")
        coEvery { suggestionsApi.sendSuggestion(any()) } throws Exception("test Exception")

        runBlocking { suggestionsRepository.addSuggestion(suggestion) }
    }
}