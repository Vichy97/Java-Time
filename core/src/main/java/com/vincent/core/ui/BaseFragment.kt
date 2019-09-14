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
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

abstract class BaseFragment (
    @LayoutRes layoutId: Int,
    private val module: Module
) : Fragment(layoutId) {

    private lateinit var navController: NavController
    private var toast: Toast? = null

    init {
        loadKoinModules(module)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        subscribeToViewModel()
    }

    @CallSuper
    protected open fun subscribeToViewModel() {

    }

    abstract fun onViewStateReceived(viewState: BaseViewState)

    private fun onToastReceived(message: String) {
        toast = Toast.makeText(context, message, LENGTH_SHORT)
        toast!!.show()
    }

    private fun onNavigationEventReceived(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.UriEvent -> navController.navigate(event.uri)
            is NavigationEvent.IdEvent -> navController.navigate(event.id, event.args)
        }
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()

        toast?.cancel()

        unloadKoinModules(module)
    }
}