package com.vincent.domain.repository

import org.koin.dsl.module

val repositoryModule = module {

    single {
        FactRepository(get())
    }

    single {
        SuggestionRepository(get())
    }
}