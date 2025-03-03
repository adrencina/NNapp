package com.example.nnapp.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nnapp.ui.auth.AuthScreen
import com.example.nnapp.ui.budget.BudgetBoardScreen
import com.example.nnapp.ui.budget.BudgetFormScreen
import com.example.nnapp.ui.camera.CameraScreen
import com.example.nnapp.ui.chat.ChatScreen
import com.example.nnapp.ui.home.HomeScreen
import com.example.nnapp.ui.projects.ProjectsScreen
import com.example.nnapp.ui.settings.SettingsScreen
import com.example.nnapp.ui.splash.SplashScreen
import com.example.nnapp.ui.viewmodel.AuthViewModel
import com.example.nnapp.ui.viewmodel.BudgetViewModel

fun NavGraphBuilder.addAuthGraph(navController: NavController) {
    composable(NavigationRoute.Splash.route) {
        SplashScreen(navController)
    }
    composable(NavigationRoute.Auth.route) {
        val authViewModel: AuthViewModel = hiltViewModel()
        AuthScreen(viewModel = authViewModel, navController = navController)
    }
}

fun NavGraphBuilder.addHomeGraph(navController: NavController) {
    composable(NavigationRoute.Home.route) {
        HomeScreen(navController)
    }
}

fun NavGraphBuilder.addChatGraph(navController: NavController) {
    composable(NavigationRoute.Chat.route) {
        ChatScreen(navController)
    }
}

fun NavGraphBuilder.addSettingsGraph(navController: NavController) {
    composable(NavigationRoute.Settings.route) {
        SettingsScreen(navController)
    }
}


fun NavGraphBuilder.addProjectsGraph(navController: NavController) {
    composable(NavigationRoute.Projects.route) {
        ProjectsScreen(navController)
    }
}

fun NavGraphBuilder.addCameraGraph(navController: NavController) {
    composable(NavigationRoute.Camera.route) {
        CameraScreen(navController)
    }
}

fun NavGraphBuilder.addBudgetGraph(navController: NavController) {
    composable(NavigationRoute.BudgetBoard.route) {
        val budgetViewModel: BudgetViewModel = hiltViewModel()
        BudgetBoardScreen(navController = navController, viewModel = budgetViewModel)
    }

    composable(
        route = NavigationRoute.BudgetForm.route,
        arguments = listOf(
            navArgument("budgetId") {
                nullable = true
                defaultValue = ""
            }
        )
    ) { backStackEntry ->
        val budgetId = backStackEntry.arguments?.getString("budgetId")
        BudgetFormScreen(
            budgetId = if (budgetId.isNullOrEmpty()) null else budgetId,
            navController = navController
        )
    }
}