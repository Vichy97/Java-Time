package com.vincent.network.util

import com.vincent.network.constants.MIME
import com.vincent.network.constants.Header

import okhttp3.Request

internal fun Request.Builder.addHeader(header: Header, MIME: MIME): Request.Builder {
    return this.header(header.toString(), MIME.toString())
}

internal fun Request.Builder.addHeader(header: Header, value: String): Request.Builder {
    return this.header(header.toString(), value)
}