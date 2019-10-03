package com.vincent.core.parsing

import com.squareup.moshi.Moshi
import org.koin.dsl.module

val moshiModule = module {

    single<Moshi> {
        Moshi.Builder()
            .build()
    }
}