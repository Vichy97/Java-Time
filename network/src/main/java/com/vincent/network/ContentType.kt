package com.vincent.network

enum class ContentType(private val contentType: String) {

    JSON("application/json"),
    FORM_URL_ENCODED("application/x-www-form-urlencoded");

    override fun toString(): String {
        return contentType
    }
}