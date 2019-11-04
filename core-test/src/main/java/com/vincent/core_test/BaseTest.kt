package com.vincent.core_test

import androidx.annotation.CallSuper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
abstract class BaseTest {

    protected val testDispatcher = TestCoroutineDispatcher()

    @CallSuper
    @Before
    open fun setup() {

    }

    @CallSuper
    @After
    open fun tearDown() {

    }
}