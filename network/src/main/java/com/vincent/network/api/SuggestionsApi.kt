package com.vincent.network.api

import com.vincent.network.model.SuggestionRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface SuggestionsApi {

    @POST("/suggestions")
    suspend fun sendSuggestion(@Body suggestionRequest: SuggestionRequest)
}