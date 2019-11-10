package com.vincent.domain.repository

import com.vincent.core_test.BaseTest
import com.vincent.database.FactDatabase
import com.vincent.database.entity.DbFact
import com.vincent.domain.model.Fact
import com.vincent.network.api.FactsApi
import com.vincent.network.model.FactResponse
import io.mockk.*

import io.objectbox.Box
import io.objectbox.query.Query
import io.reactivex.Completable
import io.reactivex.Single
import junit.framework.Assert.assertEquals

import org.junit.Test

class FactRepositoryTest : BaseTest() {

    private val factsApi = mockk<FactsApi>(relaxed = true)
    private val factsDb = mockk<FactDatabase>(relaxed = true)
    private lateinit var factRepository: FactRepository

    override fun setup() {
        super.setup()

        factRepository = FactRepository(rxProvider, factsApi, factsDb)
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

    @Test
    fun addFavorite_should_addFactToDbWhen_when_addingFavorite() {
        val fact = Fact("body")
        val slot = slot<DbFact>()

        factRepository.addFavorite(fact)
            .test()
            .assertComplete()

        verify { factsDb.add(capture(slot)) }
        assertEquals(fact.body, slot.captured.body)
    }

    @Test
    fun addFavorite_should_emitError_when_dbEmitsError() {
        val fact = Fact("body")
        val exception = Exception("exception")

        every { factsDb.add(any<DbFact>()) } returns Completable.error(exception)

        factRepository.addFavorite(fact)
            .test()
            .assertError(exception)
    }

    @Test
    fun getFavoriteFacts_should_returnFactsFromDb() {
        val facts = listOf(DbFact(body = "body"))

        every { factsDb.getAll() } returns Single.just(facts)

        factRepository.getFavoriteFacts()
            .test()
            .assertValue {
                it[0].body == facts[0].body
            }
    }

    @Test
    fun removeFact_should_deleteFactFromDb() {
        val fact = Fact("body")
        val slot = slot<DbFact>()

        every { factsDb.delete(any()) } returns Completable.complete()

        factRepository.removeFavorite(fact)
            .test()
            .assertComplete()

        verify { factsDb.delete(capture(slot)) }
        assertEquals(slot.captured.body, fact.body)
    }

    @Test
    fun removeFact_should_emitError_when_dbEmitsError() {
        val fact = Fact("body")
        val exception = Exception("exception")

        every { factsDb.delete(any()) } returns Completable.error(exception)

        factRepository.removeFavorite(fact)
            .test()
            .assertError(exception)
    }
}





















