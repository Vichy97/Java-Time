package com.vincent.core_test

import androidx.annotation.CallSuper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
abstract class BaseTest {


    @CallSuper
    @Before
    open fun setup() {

    }

    @CallSuper
    @After
    open fun tearDown() {
    }
}