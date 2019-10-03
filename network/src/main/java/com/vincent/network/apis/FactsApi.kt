package com.vincent.network.apis

import com.vincent.network.models.FactResponse
import io.reactivex.Single
import retrofit2.http.GET

interface FactsApi {

    @GET("/facts")
    fun getAllFacts(): Single<List<FactResponse>>
}