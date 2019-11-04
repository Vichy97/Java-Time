package com.vincent.core.ui

import android.net.Uri
import android.os.Bundle

import androidx.annotation.IdRes
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@ExperimentalCoroutinesApi
abstract class BaseNavigator constructor(dispatcher: CoroutineDispatcher) {

    private val uiScope = CoroutineScope(dispatcher)
    private val navigationChannel = BroadcastChannel<NavigationEvent>(1)

    @FlowPreview
    val navigationEvents: Flow<NavigationEvent> = navigationChannel.asFlow()

    protected fun navigate(@IdRes actionId: Int, arguments: Bundle? = null) = uiScope.launch {
        val navigationEvent = NavigationEvent.IdEvent(actionId, arguments)

        navigationChannel.send(navigationEvent)
    }

    protected fun navigate(uri: Uri) = uiScope.launch {
        val navigationEvent = NavigationEvent.UriEvent(uri)

        navigationChannel.send(navigationEvent)
    }
}