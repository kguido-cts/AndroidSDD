package com.android.androidsdd.ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.androidsdd.ui.TestTags
import com.android.androidsdd.ui.navigation.MainTab
import com.android.androidsdd.ui.screens.account.AccountPlaceholderScreen
import com.android.androidsdd.ui.screens.home.HomeScreen
import com.android.androidsdd.ui.screens.home.HomeViewModel
import com.android.androidsdd.ui.screens.membership.MembershipScreen
import com.android.androidsdd.ui.screens.membership.MembershipViewModel
import kotlinx.coroutines.launch

/**
 * Main screen with a Material3 bottom navigation bar hosting three tabs:
 * Home, Membership, and Account.
 *
 * Selected tab index is preserved across configuration changes via [rememberSaveable].
 */
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val tabs = MainTab.entries
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag(TestTags.MAIN_SCREEN),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar(modifier = Modifier.testTag(TestTags.BOTTOM_NAV)) {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = {
                            androidx.compose.material3.Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.label,
                            )
                        },
                        label = { Text(tab.label) },
                        modifier = Modifier.testTag(tab.testTag),
                    )
                }
            }
        },
    ) { innerPadding ->
        when (tabs[selectedIndex]) {
            MainTab.HOME -> {
                val homeViewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    viewModel = homeViewModel,
                    onFindClub = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Find a club — coming soon!")
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }

            MainTab.MEMBERSHIP -> {
                val membershipViewModel: MembershipViewModel = hiltViewModel()
                MembershipScreen(
                    viewModel = membershipViewModel,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }

            MainTab.ACCOUNT -> {
                AccountPlaceholderScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }
        }
    }
}

