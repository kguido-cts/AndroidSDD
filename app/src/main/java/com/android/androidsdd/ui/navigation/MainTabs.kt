package com.android.androidsdd.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.android.androidsdd.ui.TestTags

/** Metadata for each tab in the bottom navigation bar of [MainScreen]. */
enum class MainTab(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val testTag: String,
) {
    HOME(
        route = "tab_home",
        label = "Home",
        icon = Icons.Default.Home,
        testTag = TestTags.TAB_HOME,
    ),
    MEMBERSHIP(
        route = "tab_membership",
        label = "Membership",
        icon = Icons.Default.AccountCircle,
        testTag = TestTags.TAB_MEMBERSHIP,
    ),
    ACCOUNT(
        route = "tab_account",
        label = "Account",
        icon = Icons.Default.AccountCircle,
        testTag = TestTags.TAB_ACCOUNT,
    ),
}

