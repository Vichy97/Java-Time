package com.vincent.core.ui

import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

abstract class BaseFragment(@LayoutRes layoutId: Int, private val module: Module)
    : Fragment(layoutId) {

    private val compositeDisposable = CompositeDisposable()
    private var snackbar: Snackbar? = null
    private val navController: NavController by lazy { findNavController() }

    init {
        loadKoinModules(module)
    }

    @CallSuper
    override fun onPause() {
        super.onPause()

        snackbar?.dismiss()
        compositeDisposable.clear()
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()

        compositeDisposable.dispose()
        unloadKoinModules(module)
    }

    protected fun navigate(navigationEvent: NavigationEvent) {
        when(navigationEvent) {
            is NavigationEvent.UriEvent -> {
                navController.navigate(navigationEvent.uri)
            }
            is NavigationEvent.IdEvent -> {
                navController.navigate(navigationEvent.actionId, navigationEvent.arguments)
            }
        }
    }

    protected fun showSnackbar(message: String) {
        snackbar = view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
        }
        snackbar?.show()
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}