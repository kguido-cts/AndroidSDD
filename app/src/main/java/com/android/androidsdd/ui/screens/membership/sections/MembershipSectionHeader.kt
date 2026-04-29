package com.android.androidsdd.ui.screens.membership.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

/** Section header displayed above a membership plan card. */
@Composable
fun MembershipSectionHeader(
    title: String,
    semanticsTag: String,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag(semanticsTag),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
        )
        if (showDivider) {
            HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
        }
    }
}

