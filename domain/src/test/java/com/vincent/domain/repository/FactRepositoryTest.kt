package com.vincent.domain.repository

import com.vincent.core_test.BaseTest
import com.vincent.domain.repository.base.Result
import com.vincent.domain.repository.facts.FactAction
import com.vincent.domain.repository.facts.FactRepository
import com.vincent.domain.repository.facts.FactResult
import com.vincent.network.apis.FactsApi
import com.vincent.network.models.Fact

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

import io.reactivex.Observable
import io.reactivex.Single

import org.junit.Before
import org.junit.Test

class FactRepositoryTest : BaseTest() {

    private val factsApi = mockk<FactsApi>(relaxed = true)
    private lateinit var factRepository: FactRepository

    @Before
    override fun setup() {
        super.setup()
        factRepository = FactRepository(rxProvider, factsApi)
    }

    @Test
    fun should_makeApiCall_when_gettingFacts() {
        val actions = Observable.just(FactAction.GetAllFacts)

        actions.compose(factRepository.getAllFacts)
            .test()
            .dispose()

        verify { factsApi.getAllFacts() }
    }

    @Test
    fun should_emitSuccess_when_apiCallSucceeds() {
        val actions = Observable.just(FactAction.GetAllFacts)
        val facts = listOf(Fact("test"))
        every { factsApi.getAllFacts() } returns Single.just(facts)

        actions.compose(factRepository.getAllFacts)
            .test()
            .assertValues(
                Result.InProgress,
                FactResult.Success(facts)
            )
            .dispose()
    }

    @Test
    fun should_emitError_when_apiCallFails() {
        val actions = Observable.just(FactAction.GetAllFacts)
        val exception = Exception("test")
        every { factsApi.getAllFacts() } returns Single.error(exception)

        actions.compose(factRepository.getAllFacts)
            .test()
            .assertValues(
                Result.InProgress,
                Result.Error(exception)
            )
            .dispose()
    }
}