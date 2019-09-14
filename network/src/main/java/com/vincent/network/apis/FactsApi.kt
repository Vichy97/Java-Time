package com.vincent.network.apis

import com.vincent.network.models.Fact
import io.reactivex.Single
import retrofit2.http.GET

interface FactsApi {

    @GET("/facts")
    fun getAllFacts(): Single<List<Fact>>
}