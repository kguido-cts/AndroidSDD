package com.android.androidsdd.ui.screens.membership.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.android.androidsdd.domain.model.membership.MembershipHeader
import com.android.androidsdd.ui.TestTags

/** Header section shown at the top of the Membership screen. */
@Composable
fun MembershipHeaderSection(
    header: MembershipHeader,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .testTag(TestTags.MEMBERSHIP_HEADER),
    ) {
        Text(
            text = header.title,
            style = MaterialTheme.typography.headlineMedium,
        )

        val body = header.body
        if (!body.isNullOrBlank()) {
            Text(
                text = body,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 12.dp),
            )
        }
    }
}
