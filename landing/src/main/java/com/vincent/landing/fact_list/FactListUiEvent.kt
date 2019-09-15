package com.vincent.landing.fact_list

import com.vincent.core.ui.UiEvent

internal sealed class FactListUiEvent : UiEvent() {

    object FloatingActionButtonClicked : FactListUiEvent()
    object OnViewStart : FactListUiEvent()
}