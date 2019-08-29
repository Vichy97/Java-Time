package com.vincent.network.models

import com.squareup.moshi.Json

data class Fact(@Json(name = "body") val body: String = "")