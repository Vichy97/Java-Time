package com.vincent.core.ui

abstract class BaseViewState {
    abstract class LoadingState : BaseViewState()
    abstract class ContentState : BaseViewState()
    abstract class ErrorState: BaseViewState()
}