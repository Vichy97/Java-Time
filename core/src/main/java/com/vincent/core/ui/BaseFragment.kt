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
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
abstract class BaseFragment(
    @LayoutRes layoutId: Int,
    private val module: Module
) : Fragment(layoutId) {

    protected abstract val viewModel: BaseViewModel

    private lateinit var navController: NavController
    private var toast: Toast? = null

    private val uiJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + uiJob)

    init {
        loadKoinModules(module)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        uiJob.start()
        subscribeToViewModel()
    }

    @CallSuper
    protected fun subscribeToViewModel() {
        uiScope.launch {
            viewModel.viewStateChannel.openSubscription()
                .consumeEach {
                    onViewStateReceived(it)
                }
            viewModel.toastChannel.openSubscription()
                .consumeEach {
                    onToastReceived(it)
                }
            viewModel.navigationChannel.openSubscription()
                .consumeEach {
                    onNavigationEventReceived(it)
                }
        }
    }

    @CallSuper
    protected fun onViewStateReceived(viewState: BaseViewState) {
        when (viewState) {
            is BaseViewState.LoadingState -> renderLoadingState()
            is BaseViewState.ContentState -> renderContentState(viewState)
            is BaseViewState.ErrorState -> renderErrorState(viewState)
        }
    }

    @CallSuper
    protected fun renderLoadingState() {}

    @CallSuper
    protected fun renderContentState(viewState: BaseViewState) {}

    @CallSuper
    protected fun renderErrorState(viewState: BaseViewState) {}

    private fun onToastReceived(message: String) {
        toast = Toast.makeText(context, message, LENGTH_SHORT)
        toast!!.show()
    }

    private fun onNavigationEventReceived(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.UriEvent -> navController.navigate(event.uri)
            is NavigationEvent.IdEvent -> navController.navigate(event.id, event.args)
            is NavigationEvent.BackEvent -> navController.popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        uiJob.cancel()
        toast?.cancel()

        unloadKoinModules(module)
    }
}