package com.vincent.network.apis

import com.vincent.network.models.Suggestion
import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST

interface SuggestionsApi {

    @POST("/suggestions")
    fun sendSuggestion(@Body suggestion: Suggestion): Completable
}