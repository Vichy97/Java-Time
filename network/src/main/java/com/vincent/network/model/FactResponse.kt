package com.vincent.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FactResponse(@Json(name = "body") val body: String = "")