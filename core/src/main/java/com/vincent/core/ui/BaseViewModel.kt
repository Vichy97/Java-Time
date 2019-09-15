package com.vincent.core.ui

import androidx.lifecycle.ViewModel
import com.vincent.core.utils.ResourceProvider
import com.vincent.core.utils.RxProvider

abstract class BaseViewModel(
    protected val resourceProvider: ResourceProvider,
    protected val rxProvider: RxProvider
) : ViewModel() {

    val toastSubject = rxProvider.publishSubject<String>()
}