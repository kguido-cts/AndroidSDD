package com.android.androidsdd.ui.screens.membership

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.androidsdd.domain.model.membership.MembershipPlan
import com.android.androidsdd.ui.TestTags
import com.android.androidsdd.ui.screens.membership.sections.MembershipHeaderSection
import com.android.androidsdd.ui.screens.membership.sections.MembershipPlanCard
import com.android.androidsdd.ui.screens.membership.sections.MembershipSectionHeader

/**
 * Membership screen composable.
 *
 * Renders a vertically scrollable [LazyColumn] with five top-level items:
 * Header → Classic header → Classic card → Black Card header → Black Card card.
 */
@Composable
fun MembershipScreen(
    viewModel: MembershipViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag(TestTags.MEMBERSHIP_SCREEN),
        contentAlignment = Alignment.Center,
    ) {
        when (val state = uiState) {
            is MembershipUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.testTag(TestTags.MEMBERSHIP_LOADING))
            }

            is MembershipUiState.Error -> {
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .testTag(TestTags.MEMBERSHIP_ERROR),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = state.message,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Button(
                        onClick = viewModel::retry,
                        modifier = Modifier.testTag(TestTags.MEMBERSHIP_RETRY_BTN),
                    ) {
                        Text("Retry")
                    }
                }
            }

            is MembershipUiState.Content -> {
                val content = state.membershipContent

                // Primary happy-path: find sections by stable IDs from the JSON contract.
                val classicSection = content.sections.firstOrNull { it.id == "classic_section" }
                    ?: content.sections.firstOrNull { it.title.equals("Classic", ignoreCase = true) }

                val blackCardSection = content.sections.firstOrNull { it.id == "black_card_section" }
                    ?: content.sections.firstOrNull { it.title.equals("Black Card", ignoreCase = true) }

                val classicPlan = classicSection?.plans?.firstOrNull() ?: MembershipPlan(
                    id = "classic_missing",
                    name = "Classic",
                    tagline = null,
                    benefits = emptyList(),
                    iconKey = null,
                )

                val blackCardPlan = blackCardSection?.plans?.firstOrNull() ?: MembershipPlan(
                    id = "black_card_missing",
                    name = "Black Card",
                    tagline = null,
                    benefits = emptyList(),
                    iconKey = null,
                )

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        MembershipHeaderSection(
                            header = content.header,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    item {
                        MembershipSectionHeader(
                            title = classicSection?.title ?: "Classic",
                            semanticsTag = TestTags.MEMBERSHIP_SECTION_HEADER_CLASSIC,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    item {
                        MembershipPlanCard(
                            plan = classicPlan,
                            semanticsTag = TestTags.MEMBERSHIP_PLAN_CARD_CLASSIC,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    item {
                        MembershipSectionHeader(
                            title = blackCardSection?.title ?: "Black Card",
                            semanticsTag = TestTags.MEMBERSHIP_SECTION_HEADER_BLACK_CARD,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    item {
                        MembershipPlanCard(
                            plan = blackCardPlan,
                            semanticsTag = TestTags.MEMBERSHIP_PLAN_CARD_BLACK_CARD,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}



