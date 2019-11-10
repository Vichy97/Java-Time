package com.vincent.domain.repository

import com.vincent.core.utils.RxProvider
import com.vincent.database.FactDatabase
import com.vincent.database.entity.DbFact
import com.vincent.domain.model.Fact
import com.vincent.network.api.FactsApi

import io.reactivex.Completable
import io.reactivex.Single

class FactRepository(
    private val rxProvider: RxProvider,
    private val factsApi: FactsApi,
    private val factsDb: FactDatabase
) {

    fun getAllFacts(): Single<List<Fact>> = factsApi.getAllFacts()
        .subscribeOn(rxProvider.ioScheduler())
        .flattenAsObservable { it }
        .map { Fact(it) }
        .toList()

    fun getFavoriteFacts(): Single<List<Fact>> = factsDb.getAll()
        .flattenAsObservable { it }
        .map { Fact(it.body) }
        .toList()

    fun addFavorite(fact: Fact): Completable {
        val dbFact = DbFact(body = fact.body)
        return factsDb.add(dbFact)
    }

    fun removeFavorite(fact: Fact): Completable {
        val dbFact = DbFact(body = fact.body)
        return factsDb.delete(dbFact)
    }
}