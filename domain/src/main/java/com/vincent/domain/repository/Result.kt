package com.vincent.domain.repository

import com.vincent.network.models.Fact

sealed class Result {

    object InProgress: Result()
    object Error: Result()
    data class Success(val facts: List<Fact>): Result()
}