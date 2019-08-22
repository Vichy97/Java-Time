package com.vincent.network

import com.vincent.network.apis.FactsApi
import com.vincent.network.apis.SuggestionsApi
import com.vincent.network.interceptors.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    single {
        get<Retrofit>().create(FactsApi::class.java)
    }

    single {
        get<Retrofit>().create(SuggestionsApi::class.java)
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(get())
            .addConverterFactory(get<MoshiConverterFactory>())
            .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<HeaderInterceptor>())
            .build()
    }

    single {
        HttpLoggingInterceptor().apply {
            level = get()
        }
    }

    single {
        HeaderInterceptor()
    }

    single {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    single {
        MoshiConverterFactory.create(get())
    }
}