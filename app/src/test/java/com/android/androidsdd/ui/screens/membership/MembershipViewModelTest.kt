package com.android.androidsdd.ui.screens.membership

import com.android.androidsdd.domain.model.membership.MembershipBenefit
import com.android.androidsdd.domain.model.membership.MembershipContent
import com.android.androidsdd.domain.model.membership.MembershipContentError
import com.android.androidsdd.domain.model.membership.MembershipHeader
import com.android.androidsdd.domain.model.membership.MembershipPlan
import com.android.androidsdd.domain.model.membership.MembershipSection
import com.android.androidsdd.domain.usecase.GetMembershipContentUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MembershipViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var useCase: GetMembershipContentUseCase

    private val fakeContent = MembershipContent(
        header = MembershipHeader("Membership", "Compare plans"),
        sections = listOf(
            MembershipSection(
                id = "classic_section",
                title = "Classic",
                plans = listOf(
                    MembershipPlan(
                        id = "classic",
                        name = "Classic",
                        tagline = null,
                        benefits = listOf(MembershipBenefit(id = "b1", text = "Unlimited access")),
                        iconKey = null,
                    ),
                ),
            ),
        ),
    )

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        useCase = mockk()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init loads Loading to Content`() = runTest(testDispatcher) {
        coEvery { useCase() } returns fakeContent

        val viewModel = MembershipViewModel(useCase)
        advanceUntilIdle()

        assertInstanceOf(MembershipUiState.Content::class.java, viewModel.uiState.value)
        val content = (viewModel.uiState.value as MembershipUiState.Content).membershipContent
        assertTrue(content.sections.isNotEmpty())
        coVerify(exactly = 1) { useCase() }
    }

    @Test
    fun `failure yields Loading to Error`() = runTest(testDispatcher) {
        coEvery { useCase() } throws MembershipContentError.MissingData

        val viewModel = MembershipViewModel(useCase)
        advanceUntilIdle()

        assertInstanceOf(MembershipUiState.Error::class.java, viewModel.uiState.value)
    }

    @Test
    fun `retry triggers reload to Content`() = runTest(testDispatcher) {
        coEvery { useCase() } throws MembershipContentError.MissingData

        val viewModel = MembershipViewModel(useCase)
        advanceUntilIdle()
        assertInstanceOf(MembershipUiState.Error::class.java, viewModel.uiState.value)

        coEvery { useCase() } returns fakeContent
        viewModel.retry()
        advanceUntilIdle()

        assertInstanceOf(MembershipUiState.Content::class.java, viewModel.uiState.value)
        coVerify(exactly = 2) { useCase() }
    }
}

