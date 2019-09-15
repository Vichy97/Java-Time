package com.vincent.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

abstract class BaseDialogFragment(
    @LayoutRes private val layoutId: Int,
    private val module: Module
) : DialogFragment() {

    private lateinit var navController: NavController
    private var toast: Toast? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        subscribeToViewModel()
    }

    @CallSuper
    protected open fun subscribeToViewModel() {

    }

    abstract fun onViewStateReceived(viewState: ViewState)

    private fun onToastReceived(message: String) {
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast!!.show()
    }

    override fun onDestroy() {
        super.onDestroy()

        toast?.cancel()

        unloadKoinModules(module)
    }
}