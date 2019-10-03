package com.vincent.domain.repository

import com.vincent.network.api.FactsApi
import com.vincent.network.api.SuggestionsApi
import org.koin.dsl.module

val repositoryModule = module {

    single<FactRepository> {
        FactRepository(get<FactsApi>())
    }

    single<SuggestionRepository> {
        SuggestionRepository(get<SuggestionsApi>())
    }
}