package com.vincent.network.interceptors

import com.vincent.network.ContentType
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .addHeader("Accept", ContentType.JSON.toString())
            .addHeader("Content-Type", ContentType.FORM_URL_ENCODED.toString())
            .method(original.method(), original.body())
            .build()

        return chain.proceed(request)
    }
}