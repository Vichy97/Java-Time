package com.vincent.domain.repository

import com.vincent.network.apis.FactsApi
import com.vincent.network.apis.SuggestionsApi
import org.koin.dsl.module

val repositoryModule = module {

    single<FactRepository> {
        FactRepository(get<FactsApi>())
    }

    single<SuggestionRepository> {
        SuggestionRepository(get<SuggestionsApi>())
    }
}