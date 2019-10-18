package com.vincent.javatime

import android.app.Application

import com.facebook.stetho.Stetho
import com.vincent.core.analytics.analyticsModule
import com.vincent.core.log.CrashlyticsTree

import com.vincent.core.parsing.moshiModule
import com.vincent.core.utils.utilsModule
import com.vincent.domain.repository.repositoryModule
import com.vincent.network.networkModule

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

import timber.log.Timber

class Application : Application() {

    private val modules = listOf(
        utilsModule,
        moshiModule,
        networkModule,
        repositoryModule,
        analyticsModule
    )

    override fun onCreate() {
        super.onCreate()

        setupLogging()
        setupDependencyInjection()
        setupStetho()
    }

    private fun setupLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsTree())
        }
    }

    private fun setupDependencyInjection() {
        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(modules)
        }
    }

    private fun setupStetho() {
        Stetho.initializeWithDefaults(this)
    }
}