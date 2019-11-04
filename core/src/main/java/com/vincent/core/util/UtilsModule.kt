package com.vincent.core.util

import android.content.res.Resources
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val IO_DISPATCHER = "MAIN_DISPATCHER"
const val UI_DISPATCHER = "MAIN_DISPATCHER"

@ExperimentalCoroutinesApi
val utilsModule = module {

    single<ResourceProvider> {
        ResourceProvider(get<Resources>())
    }

    single<Resources> {
        androidContext().resources
    }

    single<CoroutineDispatcher>(named(IO_DISPATCHER)) {
        Dispatchers.IO
    }

    single<CoroutineDispatcher>(named(UI_DISPATCHER)) {
        Dispatchers.Main
    }
}