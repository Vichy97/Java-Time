package com.vincent.domain.repository

import com.vincent.domain.repository.facts.FactRepository
import com.vincent.domain.repository.suggestions.SuggestionRepository
import org.koin.dsl.module

val repositoryModule = module {

    single {
        FactRepository(get(), get())
    }

    single {
        SuggestionRepository(get(), get())
    }
}