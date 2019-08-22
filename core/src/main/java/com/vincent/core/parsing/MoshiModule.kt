package com.vincent.core.parsing

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module

val moshiModule = module {

    single {
        Moshi.Builder()
            .add(get<KotlinJsonAdapterFactory>())
            .build()
    }

    single {
        KotlinJsonAdapterFactory()
    }
}