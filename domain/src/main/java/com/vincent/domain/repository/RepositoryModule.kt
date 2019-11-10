package com.vincent.domain.repository

import com.vincent.core.utils.RxProvider
import com.vincent.database.FactDatabase
import com.vincent.database.entity.DbFact
import com.vincent.network.api.FactsApi
import com.vincent.network.api.SuggestionsApi
import io.objectbox.Box
import org.koin.dsl.module

val repositoryModule = module {

    single<FactRepository> {
        FactRepository(get<RxProvider>(), get<FactsApi>(), get<FactDatabase>())
    }

    single<SuggestionRepository> {
        SuggestionRepository(get<RxProvider>(), get<SuggestionsApi>())
    }
}