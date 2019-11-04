package com.vincent.landing.suggestion_dialog

import com.vincent.core.analytics.AnalyticsService
import com.vincent.core.analytics.Page
import com.vincent.core.util.ResourceProvider
import com.vincent.core_test.BaseTest
import com.vincent.domain.repository.SuggestionRepository
import com.vincent.landing.suggestion_dialog.validation.SuggestionValidator
import io.mockk.coEvery

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

import org.junit.Test

@ExperimentalCoroutinesApi
@FlowPreview
class SuggestionViewModelTest : BaseTest() {

    private val navigator = mockk<SuggestionNavigator>(relaxed = true)
    private val resourceProvider = mockk<ResourceProvider>(relaxed = true)
    private val analyticsService = mockk<AnalyticsService>(relaxed = true)
    private val suggestionRepository = mockk<SuggestionRepository>(relaxed = true)
    private val suggestionValidator = mockk<SuggestionValidator>(relaxed = true)

    private lateinit var viewModel: SuggestionViewModel

    override fun setup() {
        super.setup()

        viewModel = SuggestionViewModel(
            resourceProvider,
            testDispatcher,
            testDispatcher,
            navigator,
            analyticsService,
            suggestionRepository,
            suggestionValidator
        )

        every { suggestionValidator.validateEmail(any()) } returns ""
    }

    @Test
    fun should_sendPageViewEvent_when_viewModelStarts() {
        viewModel.start()

        verify { analyticsService.trackPage(Page.SUGGESTION) }
    }

    @Test
    fun should_emitViewState_when_cancelClicked() {
        viewModel.onCancelClicked()
    }

    @Test
    fun should_emitViewState_when_sendClickedWithInvalidEmail() {
        val invalidEmailError = "invalid email"
        every { suggestionValidator.validateEmail(any()) } returns invalidEmailError

        viewModel.onSendClicked()
    }

    @Test
    fun should_emitViewState_when_sendClickedWithEmptySuggestion() {
        val invalidSuggestionError = "empty suggestion"
        every { suggestionValidator.validateSuggestion(any()) } returns invalidSuggestionError

        viewModel.onSendClicked()
    }

    @Test
    fun should_emitSnackbar_when_repositoryEmitsError() {
        val exception = Exception()
        coEvery { suggestionRepository.sendSuggestion(any()) } throws exception

        viewModel.onSendClicked()
    }

    @Test
    fun should_emitViewState_when_repositoryEmitsSuccess() {
        viewModel.onSendClicked()
    }

    @Test
    fun should_clearErrors_when_suggestionTextChanged() {
        viewModel.onSuggestionTextChanged("suggestion")
    }

    @Test
    fun should_clearErrors_when_emailTextChanged() {
        viewModel.onEmailTextChanged("email")
    }
}