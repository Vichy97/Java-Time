package com.vincent.domain.repository

import com.vincent.core_test.BaseTest
import com.vincent.network.api.FactsApi
import com.vincent.network.model.FactResponse

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single

import org.junit.Test

class FactRepositoryTest : BaseTest() {

    private val factsApi = mockk<FactsApi>(relaxed = true)
    private lateinit var factRepository: FactRepository

    override fun setup() {
        super.setup()

        factRepository = FactRepository(rxProvider, factsApi)
    }

    @Test
    fun should_makeApiCall_when_gettingFacts() {
        factRepository.getAllFacts()
            .test()

        verify { factsApi.getAllFacts() }
    }

    @Test
    fun should_emitError_when_getFactsApiReturnsError() {
        val exception = Exception("test")
        every { factsApi.getAllFacts() } returns Single.error(exception)

        factRepository.getAllFacts()
            .test()
            .assertError(exception)
    }

    @Test
    fun should_emitFactListFromApi_when_apiCallSucceeds() {
        val fact = FactResponse("body")
        val facts = listOf(fact)

        every { factsApi.getAllFacts() } returns Single.just(facts)

        factRepository.getAllFacts()
            .test()
            .assertValue {
                it[0].body == facts[0].body
            }
    }
}