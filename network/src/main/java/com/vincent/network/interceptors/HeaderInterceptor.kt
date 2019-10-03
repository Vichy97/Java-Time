package com.vincent.network.interceptors

import com.vincent.network.constants.MIME
import com.vincent.network.constants.Header
import com.vincent.network.utils.addHeader

import okhttp3.Interceptor
import okhttp3.Response

internal class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder().apply {
            addHeader(Header.ACCEPT, MIME.JSON)
            addHeader(Header.CONTENT_TYPE, MIME.FORM_URL_ENCODED)
            method(original.method(), original.body())
        }.build()

        return chain.proceed(request)
    }
}