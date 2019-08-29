package com.vincent.domain.repository

import com.vincent.network.apis.FactsApi
import com.vincent.network.models.Fact

class FactRepository(private val factsApi: FactsApi) {

    suspend fun getFacts(): List<Fact> {
        return factsApi.getAllFacts()
    }
}