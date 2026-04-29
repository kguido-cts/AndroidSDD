package com.android.androidsdd.ui.screens.home.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.android.androidsdd.domain.model.home.MembershipTypesSection
import com.android.androidsdd.ui.TestTags

/** Membership types section on the Home screen. */
@Composable
fun MembershipTypesSection(
    section: MembershipTypesSection,
    onViewMemberships: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag(TestTags.HOME_MEMBERSHIP_TYPES_SECTION)
            .padding(vertical = 24.dp),
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
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
        }
        Spacer(Modifier.height(16.dp))

        if (section.plans.isEmpty()) {
            Text(
                text = "No membership plans available",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) {
                items(section.plans) { plan ->
                    Card(
                        modifier = Modifier
                            .width(200.dp)
                            .padding(end = 12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = plan.name, style = MaterialTheme.typography.titleMedium)
                            plan.priceFrom?.let { price ->
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "From $price",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                            if (plan.bullets.isNotEmpty()) {
                                Spacer(Modifier.height(8.dp))
                                plan.bullets.forEach { bullet ->
                                    Text(
                                        text = "• $bullet",
                                        style = MaterialTheme.typography.bodySmall,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        TextButton(
            onClick = onViewMemberships,
            modifier = Modifier.padding(horizontal = 8.dp),
        ) {
            Text("View memberships")
        }
    }
}

