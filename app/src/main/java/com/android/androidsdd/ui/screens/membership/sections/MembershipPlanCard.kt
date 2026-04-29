package com.android.androidsdd.ui.screens.membership.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import com.android.androidsdd.domain.model.membership.MembershipPlan

private val ClassicAccent = Color(0xFF1565C0)
private val BlackCardAccent = Color(0xFF212121)
private val BlackCardSurface = Color(0xFF2C2C2C)
private val BlackCardOnSurface = Color(0xFFEEEEEE)

/** Card composable that renders a single membership plan in a comparable structure. */
@Composable
fun MembershipPlanCard(
    plan: MembershipPlan,
    semanticsTag: String,
    modifier: Modifier = Modifier,
) {
    val isBlackCard = plan.id == "black_card"
    val accentColor = if (isBlackCard) BlackCardAccent else ClassicAccent
    val cardContainerColor = if (isBlackCard) BlackCardSurface else MaterialTheme.colorScheme.surface
    val onCardColor = if (isBlackCard) BlackCardOnSurface else MaterialTheme.colorScheme.onSurface
    val onCardMuted = if (isBlackCard) BlackCardOnSurface.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant
    val checkColor = if (isBlackCard) Color.White else accentColor

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag(semanticsTag),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = cardContainerColor),
    ) {
        Column {
            // ── Colored top band ──────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(accentColor)
                    .padding(horizontal = 20.dp, vertical = 20.dp),
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

                val price = plan.price
                if (!price.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = price,
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                        color = Color.White,
                    )
                }

                val tagline = plan.tagline
                if (!tagline.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = tagline,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.85f),
                    )
                }
            }

            // ── Benefits list ─────────────────────────────────────────────
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                if (plan.benefits.isNotEmpty()) {
                    plan.benefits.forEach { benefit ->
                        BenefitRow(
                            text = benefit.text,
                            accentColor = checkColor,
                            textColor = onCardColor,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = onCardColor.copy(alpha = 0.12f))
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // ── CTA button ────────────────────────────────────────────
                Button(
                    onClick = { /* TODO: navigate to enroll flow */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accentColor,
                        contentColor = Color.White,
                    ),
                ) {
                    Text(
                        text = "Get Started",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "No commitment. Cancel anytime.",
                    style = MaterialTheme.typography.labelSmall,
                    color = onCardMuted,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 6.dp),
                )
            }
        }
    }
}

@Composable
private fun BenefitRow(
    text: String,
    accentColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier
                .size(18.dp)
                .padding(top = 2.dp),
            tint = accentColor,
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxWidth(),
        )
    }
}
