package com.vincent.domain.repository.facts

import com.vincent.domain.repository.base.Result
import com.vincent.network.models.Fact

sealed class FactResult : Result() {

    data class Success(val facts: List<Fact>): FactResult()
}