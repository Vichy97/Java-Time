package com.vincent.network.api

import com.vincent.network.model.SuggestionRequest
import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST

interface SuggestionsApi {

    @POST("/suggestions")
    fun sendSuggestion(@Body suggestionRequest: SuggestionRequest): Completable
}