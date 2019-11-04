package com.vincent.core.ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavArgs

import com.vincent.core.R
import com.vincent.core.util.ResourceProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

import timber.log.Timber

import java.net.SocketTimeoutException
import java.net.UnknownHostException

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseViewModel<VS : BaseViewState>(
    protected val resourceProvider: ResourceProvider,
    uiDispatcher: CoroutineDispatcher,
    ioDispatcher: CoroutineDispatcher,
    navigator: BaseNavigator
) : ViewModel() {

    val supervisorJob = SupervisorJob()
    val uiScope = CoroutineScope(supervisorJob + uiDispatcher)
    val ioScope = CoroutineScope(supervisorJob + ioDispatcher)

    private val viewStateChannel = ConflatedBroadcastChannel<VS>()
    private val snackbarChannel = BroadcastChannel<String>(1)
    private val loadingChannel = BroadcastChannel<Boolean>(1)

    val navigationEvents: Flow<NavigationEvent> = navigator.navigationEvents
    val viewStateEvents: Flow<VS> = viewStateChannel.asFlow()
    val snackbarEvents: Flow<String> = snackbarChannel.asFlow()
    val loadingEvents: Flow<Boolean> = loadingChannel.asFlow()

    open fun start(arguments: NavArgs? = null) {}

    protected fun sendViewState(viewState: VS) = uiScope.launch {
        Timber.d(viewState.toString())

        viewStateChannel.send(viewState)
    }

    protected fun showSnackbar(message: String) = uiScope.launch {
        snackbarChannel.send(message)
    }

    protected fun showLoading(loading: Boolean) = uiScope.launch {
        loadingChannel.send(loading)
    }

    protected open fun onError(throwable: Throwable) {
        val error = when (throwable) {
            is SocketTimeoutException, is UnknownHostException -> {
                resourceProvider.getString(R.string.internet_connection_error)
            }
            else -> resourceProvider.getString(R.string.generic_error)
        }

        showSnackbar(error)
    }

    override fun onCleared() {
        super.onCleared()

        supervisorJob.cancel()
    }
}