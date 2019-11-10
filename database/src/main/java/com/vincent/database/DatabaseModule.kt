package com.vincent.database

import com.vincent.database.entity.DbFact
import com.vincent.database.entity.MyObjectBox

import io.objectbox.Box
import io.objectbox.BoxStore

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single<BoxStore> {
         MyObjectBox.builder()
            .androidContext(androidApplication())
            .build()
    }

    single<Box<DbFact>> {
        get<BoxStore>().boxFor(DbFact::class.java)
    }

    single<FactDatabase> {
        FactDatabase(get<Box<DbFact>>())
    }
}