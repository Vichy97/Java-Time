package com.vincent.core.utils

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

open class RxProvider {

    open val ioScheduler = Schedulers.io()
    open val uiScheduler = AndroidSchedulers.mainThread()

    fun compositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    fun <T> publishSubject(): PublishSubject<T> {
        return PublishSubject.create<T>()
    }

    fun <T> behaviorSubject(): BehaviorSubject<T> {
        return BehaviorSubject.create<T>()
    }
}