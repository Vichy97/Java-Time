package com.vincent.core.ui

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.navigation.NavArgs

import com.vincent.core.R
import com.vincent.core.utils.ResourceProvider
import com.vincent.core.utils.RxProvider

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

import timber.log.Timber

import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseViewModel<VS : BaseViewState>(
    protected val rxProvider: RxProvider,
    protected val resourceProvider: ResourceProvider,
    navigator: BaseNavigator
) : ViewModel() {

    private val compositeDisposable = rxProvider.compositeDisposable()
    private val viewStateSubject = rxProvider.behaviorSubject<VS>()
    private val snackbarSubject = rxProvider.publishSubject<String>()
    private val loadingSubject = rxProvider.behaviorSubject<Boolean>()

    val navigationEvents: Observable<NavigationEvent> = navigator.navigationEvents
    val viewStateEvents: Observable<VS> = viewStateSubject
    val snackbarEvents: Observable<String> = snackbarSubject
    val loadingEvents: Observable<Boolean> = loadingSubject

    open fun start(arguments: NavArgs? = null) {}

    @CallSuper
    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

    protected fun sendViewState(viewState: VS) {
        Timber.d(viewState.toString())

        viewStateSubject.onNext(viewState)
    }

    protected fun showSnackbar(message: String) = snackbarSubject.onNext(message)

    protected fun showLoading(loading: Boolean) = loadingSubject.onNext(loading)

    protected open fun onError(throwable: Throwable) {
        Timber.e(throwable)

        val error = when (throwable) {
            is SocketTimeoutException, is UnknownHostException -> {
                resourceProvider.getString(R.string.internet_connection_error)
            }
            else -> resourceProvider.getString(R.string.generic_error)
        }

        showSnackbar(error)
    }

    protected fun addDisposables(vararg disposables: Disposable) = compositeDisposable.addAll(*disposables)
}