package com.vincent.network.models

import com.squareup.moshi.Json

data class SuggestionRequest(
    @Json(name = "name") val name: String = "",
    @Json(name = "email") val email: String = "",
    @Json(name = "body") val body: String = ""
)