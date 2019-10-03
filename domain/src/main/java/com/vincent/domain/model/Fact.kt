package com.vincent.domain.model

import com.vincent.network.model.FactResponse

data class Fact(
    val body: String = ""
) {
    internal constructor(response: FactResponse) : this(response.body)
}