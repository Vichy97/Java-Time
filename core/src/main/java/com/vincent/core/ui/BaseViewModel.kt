package com.vincent.core.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

@ExperimentalCoroutinesApi
abstract class BaseViewModel : ViewModel() {

    val viewStateChannel = ConflatedBroadcastChannel<BaseViewState>()
    val navigationChannel = BroadcastChannel<NavigationEvent>(Channel.CONFLATED)
    val toastChannel = BroadcastChannel<String>(Channel.CONFLATED)
}