package com.android.androidsdd.ui.screens.landing

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.androidsdd.ui.TestTags

/**
 * Landing screen displayed at app launch.
 *
 * Provides two CTAs: continue as guest (→ Main) or log in (→ Login placeholder).
 * Multi-tap is prevented by disabling buttons after the first tap.
 */
@Composable
fun LandingScreen(
    onContinueAsGuest: () -> Unit,
    onLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var navigating by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
            .testTag(TestTags.LANDING_SCREEN),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "PeakFit",
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Train Harder. Live Better.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = {
                if (!navigating) {
                    navigating = true
                    onContinueAsGuest()
                }
            },
            enabled = !navigating,
            modifier = Modifier.testTag(TestTags.LANDING_GUEST_BTN),
        ) {
            Text("Continue as Guest")
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = {
                if (!navigating) {
                    navigating = true
                    onLogin()
                }
            },
            enabled = !navigating,
            modifier = Modifier.testTag(TestTags.LANDING_LOGIN_BTN),
        ) {
            Text("Log In")
        }
    }
}

