package com.vincent.network.api

import com.vincent.network.model.FactResponse
import io.reactivex.Single
import retrofit2.http.GET

interface FactsApi {

    @GET("/facts")
    fun getAllFacts(): Single<List<FactResponse>>
}