package com.vincent.network.models

import com.squareup.moshi.Json

data class FactResponse(@Json(name = "body") val body: String = "")