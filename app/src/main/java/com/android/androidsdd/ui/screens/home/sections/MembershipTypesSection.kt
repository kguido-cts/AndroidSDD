package com.android.androidsdd.ui.screens.home.sections

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.android.androidsdd.domain.model.home.MembershipPlanSummary
import com.android.androidsdd.domain.model.home.MembershipTypesSection
import com.android.androidsdd.ui.TestTags

private val BlackCardAccent = Color(0xFF212121)
private val BlackCardSurface = Color(0xFF2C2C2C)
private val BlackCardOnSurface = Color(0xFFEEEEEE)
private val ClassicAccent = Color(0xFF1565C0)

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
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                section.plans.forEachIndexed { index, plan ->
                    MembershipPlanCard(plan = plan)
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
    modifier: Modifier = Modifier,
) {
    val isBlackCard = plan.id == "black_card"

    val cardContainerColor = if (isBlackCard) BlackCardSurface else MaterialTheme.colorScheme.surface
    val contentColor = if (isBlackCard) BlackCardOnSurface else MaterialTheme.colorScheme.onSurface
    val topBandColor = if (isBlackCard) BlackCardAccent else ClassicAccent

    val borderModifier = modifier

    Card(
        modifier = borderModifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = cardContainerColor),
    ) {
        Column {
            // ── Top band (plan name + price) ──────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(topBandColor)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
            ) {
                Text(
                    text = plan.name.uppercase(),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = androidx.compose.ui.unit.TextUnit(
                            2f,
                            androidx.compose.ui.unit.TextUnitType.Sp,
                        ),
                    ),
                    color = Color.White,
                )
                plan.tagline?.let { tagline ->
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = tagline,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.85f),
                    )
                }
                plan.priceFrom?.let { price ->
                    Spacer(Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = price.substringBefore("/"),
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                            color = Color.White,
                        )
                        if (price.contains("/")) {
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "/${price.substringAfter("/")}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.75f),
                                modifier = Modifier.padding(bottom = 4.dp),
                            )
                        }
                    }
                }
            }

            // ── Feature bullets ───────────────────────────────────────────
            if (plan.bullets.isNotEmpty()) {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                    plan.bullets.forEach { bullet ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 3.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = if (isBlackCard) Color.White else topBandColor,
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
                    Spacer(Modifier.height(4.dp))
                }
            }
        }
    }
}
