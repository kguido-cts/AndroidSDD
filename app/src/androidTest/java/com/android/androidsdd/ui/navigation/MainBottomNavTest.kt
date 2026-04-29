package com.android.androidsdd.ui.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.android.androidsdd.MainActivity
import com.android.androidsdd.ui.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainBottomNavTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        // Navigate to Main
        composeRule.onNodeWithTag(TestTags.LANDING_GUEST_BTN).performClick()
    }

    @Test
    fun mainScreen_showsBottomNav_withThreeTabs() {
        composeRule.onNodeWithTag(TestTags.BOTTOM_NAV).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.TAB_HOME).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.TAB_MEMBERSHIP).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.TAB_ACCOUNT).assertIsDisplayed()
    }

    @Test
    fun tapping_membershipTab_showsMembershipPlaceholder() {
        composeRule.onNodeWithTag(TestTags.TAB_MEMBERSHIP).performClick()
        composeRule.onNodeWithTag(TestTags.MEMBERSHIP_PLACEHOLDER_SCREEN).assertIsDisplayed()
    }

    @Test
    fun tapping_accountTab_showsAccountPlaceholder() {
        composeRule.onNodeWithTag(TestTags.TAB_ACCOUNT).performClick()
        composeRule.onNodeWithTag(TestTags.ACCOUNT_PLACEHOLDER_SCREEN).assertIsDisplayed()
    }

    @Test
    fun tapping_homeTab_returnsToHome() {
        composeRule.onNodeWithTag(TestTags.TAB_MEMBERSHIP).performClick()
        composeRule.onNodeWithTag(TestTags.TAB_HOME).performClick()
        composeRule.onNodeWithTag(TestTags.HOME_SCREEN).assertIsDisplayed()
    }
}

