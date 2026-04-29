package com.android.androidsdd.ui.screens.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.android.androidsdd.ui.TestTags

/** Placeholder for the Account tab (future release). */
@Composable
fun AccountPlaceholderScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag(TestTags.ACCOUNT_PLACEHOLDER_SCREEN),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Account — Coming Soon",
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

