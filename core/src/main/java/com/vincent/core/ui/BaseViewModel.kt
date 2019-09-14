package com.vincent.core.ui

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.vincent.core.utils.ResourceProvider
import com.vincent.core.utils.RxProvider

abstract class BaseViewModel<N : BaseNavigator>(
    protected val resourceProvider: ResourceProvider,
    protected val rxProvider: RxProvider,
    protected val navigator: N
) : ViewModel() {

    val navigationSubject = navigator.navigationSubject
    val toastSubject = rxProvider.publishSubject<String>()
    val viewStateSubject = rxProvider.behaviorSubject<BaseViewState>()

    open fun start(args: Bundle? = null) {}
}