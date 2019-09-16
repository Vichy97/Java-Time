package com.vincent.domain.repository

import com.vincent.core_test.BaseTest
import com.vincent.domain.repository.base.Result
import com.vincent.domain.repository.suggestions.SuggestionAction
import com.vincent.domain.repository.suggestions.SuggestionRepository
import com.vincent.domain.repository.suggestions.SuggestionResult
import com.vincent.network.apis.SuggestionsApi
import com.vincent.network.models.Suggestion

import io.mockk.*

import io.reactivex.Completable
import io.reactivex.Observable

import org.junit.Before
import org.junit.Test

class SuggestionRepositoryTest : BaseTest() {

    private val suggestionsApi = mockk<SuggestionsApi>(relaxed = true)
    private lateinit var suggestionsRepository: SuggestionRepository

    private val suggestion = Suggestion("testName", "testEmail", "testBody")

    @Before
    override fun setup() {
        super.setup()
        suggestionsRepository = SuggestionRepository(rxProvider, suggestionsApi)
    }

    @Test
    fun should_makeApiCall_when_addingSuggestion() {
        val actions = Observable.just(SuggestionAction.AddSuggestion(suggestion))

        actions.compose(suggestionsRepository.addSuggestion)
            .test()
            .dispose()

        verify { suggestionsApi.sendSuggestion(suggestion) }
    }

    @Test
    fun should_emitError_when_apiCallFails() {
        val actions = Observable.just(SuggestionAction.AddSuggestion(suggestion))
        val exception = Exception("test")
        every { suggestionsApi.sendSuggestion(any()) } returns Completable.error(exception)

        actions.compose(suggestionsRepository.addSuggestion)
            .test()
            .assertValues(
                Result.InProgress,
                Result.Error(exception)
            )
            .dispose()
    }

    @Test
    fun should_emitSuccess_when_apiCallSucceeds() {
        val actions = Observable.just(SuggestionAction.AddSuggestion(suggestion))
        every { suggestionsApi.sendSuggestion(any()) } returns Completable.complete()

        actions.compose(suggestionsRepository.addSuggestion)
            .test()
            .assertValues(
                Result.InProgress,
                SuggestionResult.Success
            )
            .dispose()
    }
}