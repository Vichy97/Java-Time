package com.vincent.core.ui

import com.vincent.core.utils.RxProvider

abstract class BaseNavigator(rxProvider: RxProvider) {

    val navigationSubject = rxProvider.publishSubject<NavigationEvent>()
}