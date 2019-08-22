package com.vincent.core.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

@ExperimentalCoroutinesApi
abstract class BaseViewModel : ViewModel() {

    //publish subject. ConflatedBroadcastChannel = BehaviourSubhect
    val viewStateChannel = ConflatedBroadcastChannel<ViewState>()
    val navigationChannel = BroadcastChannel<Int>(Channel.CONFLATED)
    val toastChannel = BroadcastChannel<String>(Channel.CONFLATED)
}