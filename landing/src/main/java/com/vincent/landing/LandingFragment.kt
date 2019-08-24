package com.vincent.landing

import com.vincent.core.ui.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class LandingFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_landing
    }
}