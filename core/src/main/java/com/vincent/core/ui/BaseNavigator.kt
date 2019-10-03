package com.vincent.core.ui

import android.net.Uri
import android.os.Bundle

import androidx.annotation.IdRes

import com.vincent.core.utils.RxProvider

import io.reactivex.Observable

abstract class BaseNavigator(rxProvider: RxProvider) {

    private val navigationSubject = rxProvider.publishSubject<NavigationEvent>()

    val navigationEvents: Observable<NavigationEvent> = navigationSubject

    protected fun navigate(@IdRes actionId: Int, arguments: Bundle? = null) {
        val navigationEvent = NavigationEvent.IdEvent(actionId, arguments)

        navigationSubject.onNext(navigationEvent)
    }

    protected fun navigate(uri: Uri) {
        val navigationEvent = NavigationEvent.UriEvent(uri)

        navigationSubject.onNext(navigationEvent)
    }
}