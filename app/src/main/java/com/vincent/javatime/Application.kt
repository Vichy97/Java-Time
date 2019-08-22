package com.vincent.javatime

import android.app.Application
import com.vincent.core.parsing.moshiModule
import com.vincent.core.utils.utilsModule
import com.vincent.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        setupLogging()
        setupDependencyInjection()
    }

    private fun setupDependencyInjection() {
        startKoin{
            androidLogger()
            androidContext(this@Application)
            modules(modules)
        }
    }

    private fun setupLogging() {
        Timber.plant()
    }
}

private val modules = listOf(utilsModule, moshiModule, networkModule)