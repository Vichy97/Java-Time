package com.vincent.landing

import com.vincent.core.ui.BaseViewModel
import com.vincent.network.apis.FactsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class LandingViewModel(private val factsApi: FactsApi) : BaseViewModel() {
}