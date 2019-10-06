package com.vincent.core.ui

import android.os.Bundle
import androidx.lifecycle.ViewModel

import com.vincent.core.utils.RxProvider

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel<VS : BaseViewState, N : BaseNavigator>(
    protected val rxProvider: RxProvider,
    protected val navigator: N
) : ViewModel() {

    private val compositeDisposable = rxProvider.compositeDisposable()
    protected val viewStateSubject = rxProvider.behaviorSubject<VS>()
    protected val snackbarSubject = rxProvider.publishSubject<String>()
    protected val loadingSubject = rxProvider.behaviorSubject<Boolean>()

    val navigationEvents: Observable<NavigationEvent> = navigator.navigationEvents
    val viewStateEvents: Observable<VS> = viewStateSubject
    val snackbarEvents: Observable<String> = snackbarSubject
    val loadingEvents: Observable<Boolean> = loadingSubject

    open fun start(arguments: Bundle? = null) { }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    protected fun addDisposables(vararg disposables: Disposable) {
        compositeDisposable.addAll(*disposables)
    }
}