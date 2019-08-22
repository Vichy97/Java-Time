package com.vincent.core.ui

abstract class ViewState {

    abstract class LoadingState : ViewState()
    abstract class ContentState : ViewState()
    abstract class ErrorState: ViewState()
}