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
class LandingNavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun landingScreen_showsBothButtons() {
        composeRule.onNodeWithTag(TestTags.LANDING_GUEST_BTN).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.LANDING_LOGIN_BTN).assertIsDisplayed()
    }

    @Test
    fun guestButton_navigatesToMain() {
        composeRule.onNodeWithTag(TestTags.LANDING_GUEST_BTN).performClick()
        composeRule.onNodeWithTag(TestTags.MAIN_SCREEN).assertIsDisplayed()
    }

    @Test
    fun loginButton_navigatesToLoginPlaceholder() {
        composeRule.onNodeWithTag(TestTags.LANDING_LOGIN_BTN).performClick()
        composeRule.onNodeWithTag(TestTags.LOGIN_PLACEHOLDER_SCREEN).assertIsDisplayed()
    }

    @Test
    fun loginPlaceholder_backButton_returnsToLanding() {
        composeRule.onNodeWithTag(TestTags.LANDING_LOGIN_BTN).performClick()
        composeRule.onNodeWithTag(TestTags.LOGIN_BACK_BTN).performClick()
        composeRule.onNodeWithTag(TestTags.LANDING_SCREEN).assertIsDisplayed()
    }

    @Test
    fun loginPlaceholder_continueAsGuest_navigatesToMain() {
        composeRule.onNodeWithTag(TestTags.LANDING_LOGIN_BTN).performClick()
        composeRule.onNodeWithTag(TestTags.LOGIN_CONTINUE_GUEST_BTN).performClick()
        composeRule.onNodeWithTag(TestTags.MAIN_SCREEN).assertIsDisplayed()
    }
}

