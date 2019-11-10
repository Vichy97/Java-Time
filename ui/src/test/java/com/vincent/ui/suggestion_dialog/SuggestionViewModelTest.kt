package com.vincent.ui.suggestion_dialog

import com.vincent.core.analytics.AnalyticsService
import com.vincent.core.analytics.Page
import com.vincent.core.utils.ResourceProvider
import com.vincent.core_test.BaseTest
import com.vincent.domain.repository.SuggestionRepository
import com.vincent.ui.suggestion_dialog.validation.SuggestionValidator

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.observers.TestObserver

import org.junit.Test

class SuggestionViewModelTest : BaseTest() {

    private val navigator = mockk<SuggestionNavigator>(relaxed = true)
    private val resourceProvider = mockk<ResourceProvider>(relaxed = true)
    private val analyticsService = mockk<AnalyticsService>(relaxed = true)
    private val suggestionRepository = mockk<SuggestionRepository>(relaxed = true)
    private val suggestionValidator = mockk<SuggestionValidator>(relaxed = true)

    private lateinit var viewModel: SuggestionViewModel
    private lateinit var viewStateObserver: TestObserver<SuggestionViewState>
    private lateinit var loadingObserver: TestObserver<Boolean>
    private lateinit var snackbarObserver: TestObserver<String>

    override fun setup() {
        super.setup()

        viewModel = SuggestionViewModel(
            rxProvider,
            navigator,
            resourceProvider,
            analyticsService,
            suggestionRepository,
            suggestionValidator
        )

        viewStateObserver = viewModel.viewStateEvents.test()
        loadingObserver = viewModel.loadingEvents.test()
        snackbarObserver = viewModel.snackbarEvents.test()

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

        viewStateObserver.assertValue { it.dismissed }
    }

    @Test
    fun should_emitViewState_when_sendClickedWithInvalidEmail() {
        val invalidEmailError = "invalid email"
        every { suggestionValidator.validateEmail(any()) } returns invalidEmailError

        viewModel.onSendClicked()

        viewStateObserver.assertValue {
            !it.dismissed && it.emailError == invalidEmailError
        }
    }

    @Test
    fun should_emitViewState_when_sendClickedWithEmptySuggestion() {
        val invalidSuggestionError = "empty suggestion"
        every { suggestionValidator.validateSuggestion(any()) } returns invalidSuggestionError

        viewModel.onSendClicked()

        viewStateObserver.assertValue {
            !it.dismissed && it.suggestionError == invalidSuggestionError
        }
    }

    @Test
    fun should_emitSnackbar_when_repositoryEmitsError() {
        val exception = Exception()
        every { suggestionRepository.sendSuggestion(any()) } returns Completable.error(exception)

        viewModel.onSendClicked()

        loadingObserver.assertValues(true, false)
        viewStateObserver.assertNoValues()
        snackbarObserver.assertValueCount(1)
    }

    @Test
    fun should_emitViewState_when_repositoryEmitsSuccess() {
        every { suggestionRepository.sendSuggestion(any()) } returns Completable.complete()

        viewModel.onSendClicked()

        loadingObserver.assertValues(true, false)
        viewStateObserver.assertValue {
            it.dismissed
        }
    }

    @Test
    fun should_clearErrors_when_suggestionTextChanged() {
        viewModel.onSuggestionTextChanged("suggestion")

        viewStateObserver.assertValue {
            it.emailError == null && it.suggestionError == null
        }
    }

    @Test
    fun should_clearErrors_when_emailTextChanged() {
        viewModel.onEmailTextChanged("email")

        viewStateObserver.assertValue {
            it.emailError == null && it.suggestionError == null
        }
    }
}