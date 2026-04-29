package com.android.androidsdd.ui.screens.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.android.androidsdd.MainActivity
import com.android.androidsdd.di.FakeHomeContentRepositoryState
import com.android.androidsdd.ui.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeRouteTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        FakeHomeContentRepositoryState.shouldFail = false
        composeRule.onNodeWithTag(TestTags.LANDING_GUEST_BTN).performClick()
    }

    @Test
    fun homeScreen_renders_heroSection() {
        composeRule.onNodeWithTag(TestTags.HOME_HERO_SECTION).assertIsDisplayed()
    }

    @Test
    fun homeScreen_renders_allFourSections() {
        composeRule.onNodeWithTag(TestTags.HOME_FIND_CLUB_SECTION).performScrollTo()
        composeRule.onNodeWithTag(TestTags.HOME_MEMBERSHIP_TYPES_SECTION).performScrollTo()
        composeRule.onNodeWithTag(TestTags.HOME_AWARDS_SECTION).performScrollTo()
    }
}

