package com.vincent.core.ui

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.vincent.core.utils.ResourceProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

@ExperimentalCoroutinesApi
abstract class BaseViewModel(protected val resourceProvider: ResourceProvider) : ViewModel() {

    val viewStateChannel = ConflatedBroadcastChannel<BaseViewState>()
    val navigationChannel = BroadcastChannel<NavigationEvent>(Channel.CONFLATED)
    val toastChannel = BroadcastChannel<String>(Channel.CONFLATED)

    open fun start(args: Bundle? = null) {}
}