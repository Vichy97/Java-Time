package com.vincent.core_test

import com.vincent.core.utils.RxProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestRxProvider : RxProvider() {

    override fun uiScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun ioScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun computationScheduler(): Scheduler {
        return Schedulers.trampoline()
    }
}