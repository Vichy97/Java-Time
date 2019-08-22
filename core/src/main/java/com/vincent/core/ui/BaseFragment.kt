package com.vincent.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
abstract class BaseFragment : Fragment() {

    protected lateinit var navController: NavController
    protected lateinit var viewModel: BaseViewModel

    private var toast: Toast? = null

    private val uiJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + uiJob)

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    protected fun onViewStateReceived(viewState: ViewState) {
        when (viewState) {
            is ViewState.LoadingState -> renderLoadingState()
            is ViewState.ContentState -> renderContentState(viewState)
            is ViewState.ErrorState -> renderErrorState(viewState)
        }
    }

    @CallSuper
    protected fun renderLoadingState() {

    }

    @CallSuper
    protected fun renderContentState(viewState: ViewState) {

    }

    @CallSuper
    protected fun renderErrorState(viewState: ViewState) {

    }

    private fun onToastReceived(message: String) {
        toast = Toast.makeText(context, message, LENGTH_SHORT)
        toast!!.show()
    }

    private fun onNavigationEventReceived(@IdRes id: Int) {
        navController.navigate(id)
    }

    override fun onDestroy() {
        super.onDestroy()

        uiJob.cancel()
        toast?.cancel()
    }
}