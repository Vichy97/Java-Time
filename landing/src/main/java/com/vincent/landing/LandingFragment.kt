package com.vincent.landing

import com.vincent.core.ui.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class LandingFragment : BaseFragment(R.layout.fragment_landing, landingModule) {

    override val viewModel: LandingViewModel by viewModel()
}