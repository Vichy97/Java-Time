package com.vincent.network.apis

import com.vincent.network.models.Suggestion
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SuggestionsApi {

    @POST("/suggestions")
    fun sendSuggestion(@Body suggestion: Suggestion): Response<ResponseBody>
}