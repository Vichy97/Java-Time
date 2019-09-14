package com.vincent.landing.fact_list

import com.vincent.core.ui.BaseUiEvent

sealed class FactListUiEvent : BaseUiEvent() {

    object FloatingActionButtonClickedEvent : FactListUiEvent()

    object OnViewStart : FactListUiEvent()
}