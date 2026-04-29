package com.android.androidsdd.ui.screens.membership

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.android.androidsdd.ui.TestTags

/** Placeholder for the Membership tab (future release). */
@Composable
fun MembershipPlaceholderScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag(TestTags.MEMBERSHIP_PLACEHOLDER_SCREEN),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Membership — Coming Soon",
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

