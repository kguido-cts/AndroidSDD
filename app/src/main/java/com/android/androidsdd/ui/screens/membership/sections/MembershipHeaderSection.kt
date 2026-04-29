package com.android.androidsdd.ui.screens.membership.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.androidsdd.domain.model.membership.MembershipHeader
import com.android.androidsdd.ui.TestTags

private val HeaderDark = Color(0xFF1565C0)
private val HeaderMid = Color(0xFF42A5F5)

/** Full-width hero header shown at the top of the Membership screen. */
@Composable
fun MembershipHeaderSection(
    header: MembershipHeader,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(HeaderDark, HeaderMid),
                    start = Offset.Zero,
                    end = Offset(0f, Float.POSITIVE_INFINITY),
                ),
            )
            .testTag(TestTags.MEMBERSHIP_HEADER_SECTION),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            // Main headline
            Text(
                text = header.title,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                ),
                color = Color.White,
                textAlign = TextAlign.Center,
            )

            // Body subtitle
            val body = header.body
            if (!body.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.80f),
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
