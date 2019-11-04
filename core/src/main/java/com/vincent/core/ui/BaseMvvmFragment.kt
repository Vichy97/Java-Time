package com.vincent.core.ui

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*

import kotlinx.coroutines.flow.collect

import org.koin.core.module.Module

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseMvvmFragment<VS : BaseViewState>(
    @LayoutRes layoutId: Int,
    @MenuRes menuId: Int? = null,
    module: Module? = null
) : BaseFragment(layoutId, menuId, module) {

    private val navController: NavController by lazy { findNavController() }
    private val supervisorJob = SupervisorJob()
    private val uiScope = CoroutineScope(supervisorJob + Dispatchers.Main)

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
    protected open fun subscribeToViewModel() = uiScope.launch {
        viewModel.viewStateEvents.collect { onViewStateEvent(it) }
        viewModel.loadingEvents.collect { showLoading(it) }
        viewModel.navigationEvents.collect { onNavigationEvent(it) }
        viewModel.snackbarEvents.collect { showSnackbar(it) }
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

    override fun onDestroy() {
        super.onDestroy()

        supervisorJob.cancel()
    }
}