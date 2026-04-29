package com.android.androidsdd.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.androidsdd.ui.screens.landing.LandingScreen
import com.android.androidsdd.ui.screens.login.LoginPlaceholderScreen
import com.android.androidsdd.ui.screens.main.MainScreen

/**
 * Root navigation graph.
 *
 * Destinations:
 * - [Destinations.LANDING] → [LandingScreen]
 * - [Destinations.LOGIN] → [LoginPlaceholderScreen]
 * - [Destinations.MAIN] → [MainScreen]
 */
@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.LANDING,
        modifier = modifier,
    ) {
        composable(Destinations.LANDING) {
            LandingScreen(
                onContinueAsGuest = {
                    navController.navigate(Destinations.MAIN) {
                        popUpTo(Destinations.LANDING) { inclusive = true }
                    }
                },
                onLogin = {
                    navController.navigate(Destinations.LOGIN)
                },
            )
        }
        composable(Destinations.LOGIN) {
            LoginPlaceholderScreen(
                onBack = { navController.popBackStack() },
                onContinueAsGuest = {
                    navController.navigate(Destinations.MAIN) {
                        popUpTo(Destinations.LANDING) { inclusive = true }
                    }
                },
            )
        }
        composable(Destinations.MAIN) {
            MainScreen()
        }
    }
}

