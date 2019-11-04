package com.vincent.domain.repository

import com.vincent.domain.model.Fact
import com.vincent.network.api.FactsApi

class FactRepository(private val factsApi: FactsApi) {

    suspend fun getAllFacts() = factsApi.getAllFacts()
        .map { Fact(it)  }
}