package com.vincent.network.constants

internal enum class AuthorizationType(private val authorizationType: String) {
    BEARER("Bearer");

    override fun toString(): String {
        return authorizationType
    }

    operator fun plus(value: String): String{
        return this.authorizationType + value
    }
}

