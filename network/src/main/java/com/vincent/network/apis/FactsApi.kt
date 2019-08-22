package com.vincent.network.apis

import com.vincent.network.models.Fact
import retrofit2.Response
import retrofit2.http.GET

interface FactsApi {

    @GET("/facts")
    suspend fun getAllFacts(): Response<List<Fact>>

    @GET("/facts/daily")
    suspend fun getDailyFact(): Response<Fact>
}