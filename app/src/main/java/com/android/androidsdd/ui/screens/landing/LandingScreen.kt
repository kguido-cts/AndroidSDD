package com.android.androidsdd.ui.screens.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.androidsdd.R
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

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.landing),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        // Bottom-to-top gradient scrim for legibility
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to Color.Transparent,
                            0.5f to Color(0x55000000),
                            1.0f to Color(0xCC000000),
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, end = 32.dp, bottom = 76.dp)
                .testTag(TestTags.LANDING_SCREEN),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "PeakFit",
                style = MaterialTheme.typography.displayMedium.merge(
                    TextStyle(
                        color = Color.White,
                        shadow = Shadow(
                            color = Color(0x99000000),
                            blurRadius = 8f,
                        )
                    )
                ),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Train Harder. Live Better.",
                style = MaterialTheme.typography.bodyLarge.merge(
                    TextStyle(
                        color = Color(0xFFE0E0E0),
                        shadow = Shadow(
                            color = Color(0x99000000),
                            blurRadius = 6f,
                        )
                    )
                ),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    if (!navigating) {
                        navigating = true
                        onContinueAsGuest()
                    }
                },
                enabled = !navigating,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF1A1A1A),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTags.LANDING_GUEST_BTN),
            ) {
                Text("Continue as Guest", style = MaterialTheme.typography.labelLarge)
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = {
                    if (!navigating) {
                        navigating = true
                        onLogin()
                    }
                },
                enabled = !navigating,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White,
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTags.LANDING_LOGIN_BTN),
            ) {
                Text("Log In", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
