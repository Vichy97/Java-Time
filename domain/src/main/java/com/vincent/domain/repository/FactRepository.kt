package com.vincent.domain.repository

import com.vincent.domain.model.Fact
import com.vincent.network.api.FactsApi

import io.reactivex.Single

class FactRepository(private val factsApi: FactsApi) {

    fun getAllFacts(): Single<List<Fact>> = factsApi
        .getAllFacts()
        .flattenAsObservable { it }
        .map { Fact(it) }
        .toList()
}