package com.android.androidsdd.ui.screens.membership.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.android.androidsdd.domain.model.membership.MembershipPlan

/** Card composable that renders a single membership plan in a comparable structure. */
@Composable
fun MembershipPlanCard(
    plan: MembershipPlan,
    semanticsTag: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag(semanticsTag),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                // Until we ship real plan icons, we intentionally show a neutral placeholder.
                // In the future, plan.iconKey can be mapped to a drawable/vector.
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                )

                Column(modifier = Modifier.padding(start = 12.dp).fillMaxWidth()) {
                    Text(
                        text = plan.name,
                        style = MaterialTheme.typography.titleMedium,
                    )

                    val tagline = plan.tagline
                    if (!tagline.isNullOrBlank()) {
                        Text(
                            text = tagline,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp),
                        )
                    }
                }
            }

            if (plan.benefits.isNotEmpty()) {
                Spacer(modifier = Modifier.size(12.dp))
                plan.benefits.forEach { benefit ->
                    BenefitRow(
                        text = benefit.text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun BenefitRow(
    text: String,
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
            tint = MaterialTheme.colorScheme.primary,
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxWidth(),
        )
    }
}


