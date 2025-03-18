package com.example.nnapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Splash.route
    ) {
        addAuthGraph(navController)
        addHomeGraph(navController)
        addAcademyGraph(navController)
        addCameraGraph(navController)
        addSettingsGraph(navController)
        addChatGraph(navController)
        addBudgetGraph(navController)
        addProjectGraph(navController)
    }
}
