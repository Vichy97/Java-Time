package com.vincent.network.models

import com.squareup.moshi.Json

data class Suggestion(@Json(name = "name") val name: String = "",
                      @Json(name = "email") val email: String = "",
                      @Json(name = "body") val body: String = "")