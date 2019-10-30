package com.vincent.landing.fact_list

import com.vincent.core.analytics.AnalyticsService
import com.vincent.core.analytics.Page
import com.vincent.core.utils.ResourceProvider
import com.vincent.core_test.BaseTest
import com.vincent.domain.model.Fact
import com.vincent.domain.repository.FactRepository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.observers.TestObserver

import org.junit.Test

class FactListViewModelTest : BaseTest() {

    private val navigator = mockk<FactListNavigator>(relaxed = true)
    private val resourceProvider = mockk<ResourceProvider>(relaxed = true)
    private val analyticsService = mockk<AnalyticsService>(relaxed = true)
    private val factRepository = mockk<FactRepository>(relaxed = true)
    private lateinit var viewModel: FactListViewModel
    private lateinit var viewStateObserver: TestObserver<FactListViewState>
    private lateinit var loadingObserver: TestObserver<Boolean>
    private lateinit var snackbarObserver: TestObserver<String>

    override fun setup() {
        super.setup()

        viewModel = FactListViewModel(
            rxProvider,
            resourceProvider,
            navigator,
            analyticsService,
            factRepository
        )
        viewStateObserver = viewModel.viewStateEvents.test()
        loadingObserver = viewModel.loadingEvents.test()
        snackbarObserver = viewModel.snackbarEvents.test()
    }

    @Test
    fun should_sendPageViewEvent_when_viewModelStarts() {
        viewModel.start()

        verify { analyticsService.trackPage(Page.FACT_LIST) }
    }

    @Test
    fun should_getFactsFromRepository_when_viewModelStarts() {
        viewModel.start()

        verify { factRepository.getAllFacts() }
    }

    @Test
    fun should_emitSnackBarMessage_when_repositoryEmitsError() {
        val exception = Exception("exception")
        every { factRepository.getAllFacts() } returns Single.error(exception)

        viewModel.start()

        loadingObserver.assertValues(true, false)
        snackbarObserver.assertValueCount(1)
    }

    @Test
    fun should_emitViewState_when_repositoryReturnsFacts() {
        val fact = Fact("someFact")
        val facts = listOf(fact)
        every { factRepository.getAllFacts() } returns Single.just(facts)

        viewModel.start()

        loadingObserver.assertValues(true, false)
        viewStateObserver.assertValue {
            it.facts == facts
        }
    }

    @Test
    fun should_getFactsFromRepository_when_swipeToRefresh() {
        viewModel.onSwipeToRefresh()

        verify { factRepository.getAllFacts() }
    }

    @Test
    fun should_openSuggestionDialog_when_floatingActionButtonClicked() {
        viewModel.onFloatingActionButtonClicked()

        verify { navigator.showSuggestionDialog() }
    }
}