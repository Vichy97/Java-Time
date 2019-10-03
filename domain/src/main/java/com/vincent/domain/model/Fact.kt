package com.vincent.domain.model

import com.vincent.network.models.FactResponse

data class Fact(
    val body: String = ""
) {
    internal constructor(response: FactResponse) : this(response.body)
}