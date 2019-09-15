package com.vincent.domain.repository.base

abstract class Result {

    object InProgress: Result()
    data class Error(val e: Throwable): Result()
}