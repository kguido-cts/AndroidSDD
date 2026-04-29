package com.android.androidsdd.ui.screens.membership

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.android.androidsdd.MainActivity
import com.android.androidsdd.di.FakeMembershipContentRepositoryState
import com.android.androidsdd.domain.model.membership.MembershipBenefit
import com.android.androidsdd.domain.model.membership.MembershipContent
import com.android.androidsdd.domain.model.membership.MembershipHeader
import com.android.androidsdd.domain.model.membership.MembershipPlan
import com.android.androidsdd.domain.model.membership.MembershipSection
import com.android.androidsdd.ui.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MembershipRouteTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        FakeMembershipContentRepositoryState.shouldFail = false
        FakeMembershipContentRepositoryState.content = defaultContent()
        composeRule.onNodeWithTag(TestTags.LANDING_GUEST_BTN).performClick()
    }

    @Test
    fun membershipRoute_success_rendersHeaderAndSections_andScrollsToBlackCard() {
        composeRule.onNodeWithTag(TestTags.TAB_MEMBERSHIP).performClick()

        composeRule.onNodeWithTag(TestTags.MEMBERSHIP_HEADER).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.MEMBERSHIP_SECTION_HEADER_CLASSIC).assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.MEMBERSHIP_PLAN_CARD_BLACK_CARD).performScrollTo()
        composeRule.onNodeWithTag(TestTags.MEMBERSHIP_SECTION_HEADER_BLACK_CARD).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.MEMBERSHIP_PLAN_CARD_BLACK_CARD).assertIsDisplayed()
    }

    @Test
    fun membershipRoute_errorThenRetry_showsError_andRecoversToContent() {
        FakeMembershipContentRepositoryState.shouldFail = true

        composeRule.onNodeWithTag(TestTags.TAB_MEMBERSHIP).performClick()
        composeRule.onNodeWithTag(TestTags.MEMBERSHIP_ERROR).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.MEMBERSHIP_RETRY_BTN).assertIsDisplayed()

        FakeMembershipContentRepositoryState.shouldFail = false
        composeRule.onNodeWithTag(TestTags.MEMBERSHIP_RETRY_BTN).performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithTag(TestTags.MEMBERSHIP_HEADER)
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithTag(TestTags.MEMBERSHIP_HEADER).assertIsDisplayed()
    }

    @Test
    fun membershipRoute_displaysAtLeastThreeBenefitsForEachPlan() {
        composeRule.onNodeWithTag(TestTags.TAB_MEMBERSHIP).performClick()

        // Classic benefits
        composeRule.onNodeWithText("Unlimited gym access").assertIsDisplayed()
        composeRule.onNodeWithText("Access to your home club").assertIsDisplayed()
        composeRule.onNodeWithText("Free fitness orientation").assertIsDisplayed()

        // Black card benefits (may require scrolling)
        composeRule.onNodeWithText("All-club access").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText("Guest privileges").assertIsDisplayed()
        composeRule.onNodeWithText("Premium perks (placeholder)").assertIsDisplayed()
    }

    @Test
    fun membershipRoute_longBenefitText_wrapsAndIsReadable() {
        FakeMembershipContentRepositoryState.content = contentWithLongBenefit()

        composeRule.onNodeWithTag(TestTags.TAB_MEMBERSHIP).performClick()

        val longText = "This is a very long benefit description designed to verify that the text wraps across multiple lines on small screens without truncation or horizontal scrolling."
        composeRule.onNodeWithText(longText, substring = true).performScrollTo().assertIsDisplayed()
    }
}

private fun defaultContent(): MembershipContent = MembershipContent(
    header = MembershipHeader(
        title = "Membership",
        body = "Compare Classic and Black Card memberships.",
    ),
    sections = listOf(
        MembershipSection(
            id = "classic_section",
            title = "Classic",
            plans = listOf(
                MembershipPlan(
                    id = "classic",
                    name = "Classic",
                    tagline = "Best for everyday gym-goers",
                    iconKey = null,
                    benefits = listOf(
                        MembershipBenefit("classic_b1", "Unlimited gym access"),
                        MembershipBenefit("classic_b2", "Access to your home club"),
                        MembershipBenefit("classic_b3", "Free fitness orientation"),
                    ),
                ),
            ),
        ),
        MembershipSection(
            id = "black_card_section",
            title = "Black Card",
            plans = listOf(
                MembershipPlan(
                    id = "black_card",
                    name = "Black Card",
                    tagline = "Best for committed athletes",
                    iconKey = null,
                    benefits = listOf(
                        MembershipBenefit("black_b1", "All-club access"),
                        MembershipBenefit("black_b2", "Guest privileges"),
                        MembershipBenefit("black_b3", "Premium perks (placeholder)"),
                    ),
                ),
            ),
        ),
    ),
)

private fun contentWithLongBenefit(): MembershipContent {
    val longText = "This is a very long benefit description designed to verify that the text wraps across multiple lines on small screens without truncation or horizontal scrolling."

    return defaultContent().copy(
        sections = listOf(
            defaultContent().sections[0].copy(
                plans = listOf(
                    defaultContent().sections[0].plans[0].copy(
                        benefits = listOf(
                            MembershipBenefit("classic_b1", longText),
                            MembershipBenefit("classic_b2", "Access to your home club"),
                            MembershipBenefit("classic_b3", "Free fitness orientation"),
                        ),
                    ),
                ),
            ),
            defaultContent().sections[1],
        ),
    )
}



