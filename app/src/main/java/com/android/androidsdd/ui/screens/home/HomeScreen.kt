package com.android.androidsdd.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.android.androidsdd.ui.TestTags
import com.android.androidsdd.ui.screens.home.sections.AwardsSection
import com.android.androidsdd.ui.screens.home.sections.FindAClubSection
import com.android.androidsdd.ui.screens.home.sections.HomeHeroSection
import com.android.androidsdd.ui.screens.home.sections.MembershipTypesSection

/**
 * Home screen composable.
 *
 * Renders a vertically scrollable [LazyColumn] with four sections driven by [HomeViewModel].
 * Shows a loading indicator, error state with retry, or content.
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onFindClub: () -> Unit,
    onViewMemberships: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag(TestTags.HOME_SCREEN),
        contentAlignment = Alignment.Center,
    ) {
        when (val state = uiState) {
            is HomeUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.testTag(TestTags.HOME_LOADING))
            }

            is HomeUiState.Error -> {
                androidx.compose.foundation.layout.Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .testTag(TestTags.HOME_ERROR),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = state.message,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                    androidx.compose.foundation.layout.Spacer(
                        modifier = Modifier.padding(top = 16.dp),
                    )
                    Button(
                        onClick = viewModel::retry,
                        modifier = Modifier.testTag(TestTags.HOME_RETRY_BTN),
                    ) {
                        Text("Retry")
                    }
                }
            }

            is HomeUiState.Content -> {
                val content = state.homeContent
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        HomeHeroSection(
                            hero = content.hero,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    item {
                        FindAClubSection(
                            section = content.findAClub,
                            onFindClub = onFindClub,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    item {
                        MembershipTypesSection(
                            section = content.membershipTypes,
                            onViewMemberships = onViewMemberships,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    item {
                        AwardsSection(
                            section = content.awards,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}

