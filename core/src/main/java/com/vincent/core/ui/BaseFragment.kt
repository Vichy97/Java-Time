package com.vincent.core.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

abstract class BaseFragment (
    @LayoutRes layoutId: Int,
    private val module: Module
) : Fragment(layoutId) {

    protected lateinit var navController: NavController
    protected val compositeDisposable = CompositeDisposable()

    private var toast: Toast? = null

    init {
        loadKoinModules(module)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
    }

    private fun onToastReceived(message: String) {
        toast = Toast.makeText(context, message, LENGTH_SHORT)
        toast!!.show()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()

        toast?.cancel()
        compositeDisposable.clear() //TODO: maybe put this in onStop or onPause?

        unloadKoinModules(module)
    }
}