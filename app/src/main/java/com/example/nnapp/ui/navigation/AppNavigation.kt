package com.example.nnapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Splash.route
    ) {
        addAuthGraph(navController)

        addHomeGraph(navController)

        addProjectsGraph(navController)
        addCameraGraph(navController)
        addSettingsGraph(navController)
        addChatGraph(navController)

        addBudgetGraph(navController)
    }
}
