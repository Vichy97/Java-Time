package com.vincent.landing.fact_list

import com.vincent.core.ui.BaseViewState
import com.vincent.domain.model.Fact

internal data class FactListViewState(val facts: List<Fact>) : BaseViewState()