package com.vincent.domain.repository.facts

import com.vincent.domain.repository.base.Action

sealed class FactAction : Action() {

    object GetAllFacts : FactAction()
}