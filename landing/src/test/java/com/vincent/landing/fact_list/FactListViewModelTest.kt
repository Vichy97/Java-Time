package com.vincent.landing.fact_list

import com.vincent.core.analytics.AnalyticsService
import com.vincent.core.analytics.Page
import com.vincent.core.util.ResourceProvider
import com.vincent.core_test.BaseTest
import com.vincent.domain.model.Fact
import com.vincent.domain.repository.FactRepository

import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

import kotlinx.coroutines.test.runBlockingTest

import org.junit.Test

@ExperimentalCoroutinesApi
@FlowPreview
class FactListViewModelTest : BaseTest() {

    private val navigator = mockk<FactListNavigator>(relaxed = true)
    private val resourceProvider = mockk<ResourceProvider>(relaxed = true)
    private val analyticsService = mockk<AnalyticsService>(relaxed = true)
    private val factRepository = mockk<FactRepository>(relaxed = true)
    private lateinit var viewModel: FactListViewModel

    override fun setup() {
        super.setup()

        viewModel = FactListViewModel(
            resourceProvider,
            testDispatcher,
            testDispatcher,
            navigator,
            analyticsService,
            factRepository
        )
    }

    override fun tearDown() {
        super.tearDown()

        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun should_sendPageViewEvent_when_viewModelStarts() {
        viewModel.start()

        verify { analyticsService.trackPage(Page.FACT_LIST) }
    }

    @Test
    fun should_getFactsFromRepository_when_viewModelStarts() = runBlockingTest {
        viewModel.start()

        coVerify { factRepository.getAllFacts() }
    }

    @Test
    fun should_emitSnackBarMessage_when_repositoryEmitsError() = runBlockingTest {
        val exception = Exception("exception")
        coEvery { factRepository.getAllFacts() } throws exception
        every { resourceProvider.getString(any()) } returns "error!"

        var actual = ""
        val job = launch {
            viewModel.snackbarEvents
                .collect {
                actual = it
            }
        }

        viewModel.start()

        assertEquals(exception.message, actual)

        job.cancel()
    }


    @Test
    fun should_emitViewState_when_repositoryReturnsFacts() {
        val fact = Fact("someFact")
        val facts = listOf(fact)
        coEvery { factRepository.getAllFacts() } returns facts

        viewModel.start()
    }

    @Test
    fun should_getFactsFromRepository_when_swipeToRefresh() {
        viewModel.onSwipeToRefresh()

        coVerify { factRepository.getAllFacts() }
    }

    @Test
    fun should_openSuggestionDialog_when_floatingActionButtonClicked() {
        viewModel.onFloatingActionButtonClicked()

        verify { navigator.showSuggestionDialog() }
    }
}
