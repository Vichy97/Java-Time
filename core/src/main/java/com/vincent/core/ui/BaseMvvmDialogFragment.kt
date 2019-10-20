package com.vincent.core.ui

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

import com.vincent.core.utils.RxProvider
import io.reactivex.disposables.Disposable

import org.koin.android.ext.android.inject
import org.koin.core.module.Module

import timber.log.Timber

abstract class BaseMvvmDialogFragment<VS : BaseViewState>(
    @LayoutRes layoutId: Int,
    module: Module?
) : BaseDialogFragment(layoutId, module) {

    protected val rxProvider: RxProvider by inject()
    private val compositeDisposable = rxProvider.compositeDisposable()
    private val navController: NavController by lazy { findNavController() }

    protected abstract val viewModel: BaseViewModel<VS>

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.start()
    }

    @CallSuper
    override fun onStart() {
        super.onStart()

        subscribeToViewModel()
    }

    @CallSuper
    override fun onStop() {
        super.onStop()

        compositeDisposable.clear()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
    }

    @CallSuper
    protected open fun subscribeToViewModel() {
        val viewStateDisposable = viewModel.viewStateEvents
            .observeOn(rxProvider.uiScheduler())
            .subscribe({ onViewStateEvent(it) }, { Timber.e(it) })
        val navigationDisposable = viewModel.navigationEvents
            .observeOn(rxProvider.uiScheduler())
            .subscribe({ onNavigationEvent(it) }, { Timber.e(it) })
        val snackbarDisposable = viewModel.snackbarEvents
            .observeOn(rxProvider.uiScheduler())
            .subscribe({ showSnackbar(it) }, { Timber.e(it) })
        val loadingDisposable = viewModel.loadingEvents
            .observeOn(rxProvider.uiScheduler())
            .subscribe({ showLoading(it) }, { Timber.e(it) })

        addDisposables(viewStateDisposable, navigationDisposable, snackbarDisposable, loadingDisposable)
    }

    protected abstract fun onViewStateEvent(viewState: VS)

    private fun onNavigationEvent(navigationEvent: NavigationEvent) {
        when (navigationEvent) {
            is NavigationEvent.UriEvent -> {
                navController.navigate(navigationEvent.uri)
            }
            is NavigationEvent.IdEvent -> {
                navController.navigate(navigationEvent.actionId, navigationEvent.arguments)
            }
        }
    }

    protected fun addDisposables(vararg disposables: Disposable) {
        compositeDisposable.addAll(*disposables)
    }
}