package com.android.androidsdd.ui.screens.home.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.android.androidsdd.domain.model.home.AwardItem
import com.android.androidsdd.domain.model.home.AwardsSection
import com.android.androidsdd.ui.TestTags

private val AwardsDarkBlue = Color(0xFF0D1B3E)
private val AwardsAccent = Color(0xFF5B9BD5)

/** Awards/highlights section on the Home screen. */
@Composable
fun AwardsSection(
    section: AwardsSection,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag(TestTags.HOME_AWARDS_SECTION)
            .padding(vertical = 32.dp),
    ) {
        // Header
        Text(
            text = section.title,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = "Recognised by industry leaders for quality, community and commitment to your health.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(Modifier.height(24.dp))

        if (section.items.isEmpty()) {
            Text(
                text = "No awards to display",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        } else {
            // Regular Row with horizontalScroll + IntrinsicSize.Max so all cards share the tallest height
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .height(IntrinsicSize.Max)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                section.items.forEach { award ->
                    AwardCard(
                        award = award,
                        modifier = Modifier.fillMaxHeight(),
                    )
                }
            }
        }
    }
}

@Composable
private fun AwardCard(
    award: AwardItem,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.width(280.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = AwardsDarkBlue),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            // Subtitle badge (year / organisation)
            award.stat?.let { subtitle ->
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = AwardsAccent.copy(alpha = 0.2f),
                ) {
                    Text(
                        text = subtitle,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = AwardsAccent,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Spacer(Modifier.height(12.dp))
            }

            // Title
            Text(
                text = award.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
            )

            Spacer(Modifier.height(12.dp))

            // Description — pushed to fill remaining space so cards align
            Text(
                text = award.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.75f),
            )
        }
    }
}

