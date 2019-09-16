package com.vincent.core_test

import com.vincent.core.utils.RxProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestRxProvider : RxProvider() {

    override fun getUiScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun getIoScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun getComputationScheduler(): Scheduler {
        return Schedulers.trampoline()
    }
}