package com.vincent.network.constants

internal enum class Header(private val headerName: String) {
    ACCEPT("Accept"),
    ACCESS_CONTROL_REQUEST_METHOD("Access-Control-Request-Method"),
    CONTENT_TYPE("Content-Type"),
    AUTHORIZATION("Authorization"),
    CACHE_CONTROL("Cache-Control"),
    CONTENT_LENGTH("Content-Length"),
    COOKIE("Cookie"),
    DATE("Date");

    override fun toString(): String {
        return headerName
    }
}