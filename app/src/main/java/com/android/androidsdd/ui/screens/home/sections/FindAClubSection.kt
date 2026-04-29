package com.android.androidsdd.ui.screens.home.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.android.androidsdd.domain.model.home.FindAClubSection
import com.android.androidsdd.ui.TestTags

/** "Find a club" section on the Home screen. */
@Composable
fun FindAClubSection(
    section: FindAClubSection,
    onFindClub: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag(TestTags.HOME_FIND_CLUB_SECTION)
            .padding(horizontal = 16.dp, vertical = 24.dp),
    ) {
        Text(
            text = section.title,
            style = MaterialTheme.typography.headlineSmall,
        )
        section.description?.let { desc ->
            Spacer(Modifier.height(4.dp))
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Spacer(Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (section.clubs.isNotEmpty()) {
                    val club = section.clubs.first()
                    Text(text = club.name, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = club.shortDescription,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                } else {
                    Text(
                        text = "No clubs available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Spacer(Modifier.height(12.dp))
                section.ctaLabel?.let { label ->
                    Button(onClick = onFindClub) { Text(label) }
                }
            }
        }
    }
}

