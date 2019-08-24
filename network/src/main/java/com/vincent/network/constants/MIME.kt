package com.vincent.network.constants

internal enum class MIME(private val mime: String) {

    JSON("application/json"),
    FORM_URL_ENCODED("application/x-www-form-urlencoded");

    override fun toString(): String {
        return mime
    }
}