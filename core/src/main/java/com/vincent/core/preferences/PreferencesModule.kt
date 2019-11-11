package com.vincent.core.preferences

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private const val SHARED_PREFERENCES_NAME = "java-time-preferences"

val preferencesModule = module {

    single<Preferences> {
        Preferences(get<SharedPreferences>())
    }

    single<SharedPreferences> {
        androidApplication().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}