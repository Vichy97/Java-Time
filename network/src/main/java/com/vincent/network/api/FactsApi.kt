package com.vincent.network.api

import com.vincent.network.model.FactResponse
import retrofit2.http.GET

interface FactsApi {

    @GET("/facts")
    suspend fun getAllFacts(): List<FactResponse>
}