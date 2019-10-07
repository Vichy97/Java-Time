package com.vincent.core.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val analyticsModule = module {

    single<FirebaseAnalytics> {
        FirebaseAnalytics.getInstance(androidContext())
    }

    single<AnalyticsService>() {
        AnalyticsService(get<FirebaseAnalytics>())
    }
}