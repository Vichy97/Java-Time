package com.vincent.domain.repository

import com.vincent.network.apis.FactsApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FactRepositoryTest {

    private val factsApi = mockk<FactsApi>(relaxed = true)
    private lateinit var factRepository: FactRepository

    @Before
    fun setUp() {
        factRepository = FactRepository(factsApi)
    }

    @Test
    fun should_makeApiCall_when_gettingFacts() {
        runBlocking { factRepository.getFacts() }

        coVerify { factsApi.getAllFacts() }
    }

    @Test(expected = Exception::class)
    fun should_throwException_when_apiCallFails() {
        coEvery { factsApi.getAllFacts() } throws Exception("test exception")

        runBlocking { factRepository.getFacts() }
    }
}