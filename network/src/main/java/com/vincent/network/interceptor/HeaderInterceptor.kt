package com.vincent.network.interceptor

import com.vincent.network.constants.MIME
import com.vincent.network.constants.Header
import com.vincent.network.util.addHeader

import okhttp3.Interceptor
import okhttp3.Response

internal class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder().apply {
            addHeader(Header.ACCEPT, MIME.JSON)
            method(original.method(), original.body())
        }.build()

        return chain.proceed(request)
    }
}