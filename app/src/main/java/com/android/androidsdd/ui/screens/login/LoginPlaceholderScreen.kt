package com.android.androidsdd.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.androidsdd.ui.TestTags

/** Placeholder screen for the Login flow (coming soon). */
@Composable
fun LoginPlaceholderScreen(
    onBack: () -> Unit,
    onContinueAsGuest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
            .testTag(TestTags.LOGIN_PLACEHOLDER_SCREEN),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Login — Coming Soon",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Full login support will be available in a future release.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(48.dp))
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.testTag(TestTags.LOGIN_BACK_BTN),
        ) {
            Text("Go Back")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onContinueAsGuest,
            modifier = Modifier.testTag(TestTags.LOGIN_CONTINUE_GUEST_BTN),
        ) {
            Text("Continue as Guest")
        }
    }
}

