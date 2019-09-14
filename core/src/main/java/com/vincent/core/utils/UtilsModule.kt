package com.vincent.core.utils

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilsModule = module {

    single {
        RxProvider()
    }

    single {
        ResourceProvider(get())
    }

    single {
        androidContext().resources
    }
}