package com.vincent.core.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

open class RxProvider {

    open fun getUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    open fun getIoScheduler(): Scheduler {
        return Schedulers.io()
    }

    open fun getComputationScheduler(): Scheduler {
        return Schedulers.computation()
    }

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