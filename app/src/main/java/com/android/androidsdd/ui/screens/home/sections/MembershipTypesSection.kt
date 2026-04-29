package com.android.androidsdd.ui.screens.home.sections

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.android.androidsdd.domain.model.home.MembershipPlanSummary
import com.android.androidsdd.domain.model.home.MembershipTypesSection
import com.android.androidsdd.ui.TestTags

/** Membership types section on the Home screen. */
@Composable
fun MembershipTypesSection(
    section: MembershipTypesSection,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag(TestTags.HOME_MEMBERSHIP_TYPES_SECTION)
            .padding(vertical = 32.dp),
    ) {
        // Header
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = section.title,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
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

        Spacer(Modifier.height(24.dp))

        if (section.plans.isEmpty()) {
            Text(
                text = "No membership plans available",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        } else {
            // Determine "popular" plan — middle index if 3+ plans, else last
            val popularIndex = if (section.plans.size >= 3) section.plans.size / 2 else section.plans.lastIndex

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                section.plans.forEachIndexed { index, plan ->
                    val isPopular = index == popularIndex
                    MembershipPlanCard(
                        plan = plan,
                        isPopular = isPopular,
                    )
                    if (index < section.plans.lastIndex) Spacer(Modifier.height(16.dp))
                }
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun MembershipPlanCard(
    plan: MembershipPlanSummary,
    isPopular: Boolean,
    modifier: Modifier = Modifier,
) {
    val contentColor = if (isPopular)
        MaterialTheme.colorScheme.onPrimaryContainer
    else
        MaterialTheme.colorScheme.onSurface

    val accentColor = if (isPopular)
        MaterialTheme.colorScheme.onPrimaryContainer
    else
        MaterialTheme.colorScheme.primary

    val borderModifier = if (isPopular) {
        modifier.border(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(16.dp),
        )
    } else modifier

    Card(
        modifier = borderModifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isPopular) 6.dp else 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isPopular)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {


            // Plan name
            Text(
                text = plan.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = contentColor,
            )

            // Tagline
            plan.tagline?.let { tagline ->
                Spacer(Modifier.height(4.dp))
                Text(
                    text = tagline,
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColor.copy(alpha = 0.7f),
                )
            }

            // Price
            plan.priceFrom?.let { price ->
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = price.substringBefore("/"),
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = accentColor,
                    )
                    if (price.contains("/")) {
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "/${price.substringAfter("/")}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 4.dp),
                        )
                    }
                }
            }

            // Feature bullets with checkmarks
            if (plan.bullets.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))
                plan.bullets.forEach { bullet ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 3.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = bullet,
                            style = MaterialTheme.typography.bodySmall,
                            color = contentColor,
                        )
                    }
                }
            }

            Spacer(Modifier.height(4.dp))
        }
    }
}
