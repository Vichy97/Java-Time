package com.vincent.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import com.vincent.network.apis.FactsApi
import com.vincent.network.apis.SuggestionsApi
import com.vincent.network.interceptors.HeaderInterceptor

import okhttp3.OkHttpClient

import org.koin.dsl.module

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    single<FactsApi> {
        get<Retrofit>().create(FactsApi::class.java)
    }

    single<SuggestionsApi> {
        get<Retrofit>().create(SuggestionsApi::class.java)
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(get())
            .addConverterFactory(get<MoshiConverterFactory>())
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder().apply {
            addNetworkInterceptor(get<HeaderInterceptor>())
            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(get<StethoInterceptor>())
            }
        }.build()
    }

    single<StethoInterceptor> {
        StethoInterceptor()
    }

    single<HeaderInterceptor> {
        HeaderInterceptor()
    }

    single<MoshiConverterFactory> {
        MoshiConverterFactory.create(get<Moshi>())
    }

    single<RxJava2CallAdapterFactory> {
        RxJava2CallAdapterFactory.create()
    }
}