package com.vincent.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.vincent.network.apis.FactsApi
import com.vincent.network.apis.SuggestionsApi
import com.vincent.network.interceptors.HeaderInterceptor
import okhttp3.OkHttpClient
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
        OkHttpClient.Builder().apply {
            addInterceptor(get<HeaderInterceptor>())
            if (BuildConfig.DEBUG) {
                addInterceptor(get<StethoInterceptor>())
            }
        }.build()
    }

    single {
        StethoInterceptor()
    }

    single {
        HeaderInterceptor()
    }

    single {
        MoshiConverterFactory.create(get())
    }
}