package com.vincent.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

import com.google.android.material.snackbar.Snackbar

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

abstract class BaseDialogFragment(
    @LayoutRes private val layoutId: Int,
    private val module: Module
) : DialogFragment() {

    private val compositeDisposable = CompositeDisposable()
    private var snackbar: Snackbar? = null
    private val navController: NavController by lazy { findNavController() }

    init {
        loadKoinModules(module)
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(layoutId, container, false)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    @CallSuper
    override fun onStop() {
        super.onStop()

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
        view?.let {
            snackbar = Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
        }
        snackbar?.show()
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    protected fun addDisposables(vararg disposables: Disposable) {
        compositeDisposable.addAll(*disposables)
    }
}