package com.vincent.landing.fact_list

import com.vincent.core.utils.ResourceProvider
import com.vincent.core_test.BaseTest
import com.vincent.domain.repository.FactRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FactListViewModelTest : BaseTest() {

    private val resourceProvider = mockk<ResourceProvider>(relaxed = true)
    private val navigator = mockk<FactListNavigator>(relaxed = true)
    private val factRepository = mockk<FactRepository>(relaxed = true)

    private lateinit var viewModel: FactListViewModel

    @Before
    override fun setup() {
        super.setup()
        viewModel = FactListViewModel(resourceProvider, testScope, navigator, factRepository)
    }

    @Test
    fun should_getFacts_when_viewModelIsStarted() {
        viewModel.start(null)

        coVerify {
            factRepository.getFacts()
        }
    }

    @Test
    fun should_navigateToSuggestionFragment_when_floatingActionButtonClicked() {
        viewModel.onFloatingActionButtonClicked()

        coVerify {
            navigator.showSuggestionDialog()
        }
    }
}