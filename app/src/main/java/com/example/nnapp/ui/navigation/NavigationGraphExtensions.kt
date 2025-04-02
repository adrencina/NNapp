package com.example.nnapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.nnapp.ui.academy.AcademyScreen
import com.example.nnapp.ui.auth.AuthScreen
import com.example.nnapp.ui.budget.creation.BudgetCreationScreen
import com.example.nnapp.ui.budget.edition.BudgetEditScreen
import com.example.nnapp.ui.budget.view.BudgetViewScreen
import com.example.nnapp.ui.budget.creation.MaterialEntryScreen
import com.example.nnapp.ui.budget.creation.BudgetFinalizeScreen
import com.example.nnapp.ui.camera.CameraScreen
import com.example.nnapp.ui.chat.ChatScreen
import com.example.nnapp.ui.home.HomeScreen
import com.example.nnapp.ui.projects.ProjectDetailsScreen
import com.example.nnapp.ui.settings.SettingsScreen
import com.example.nnapp.ui.splash.SplashScreen
import com.example.nnapp.ui.viewmodel.AuthViewModel
import com.example.nnapp.ui.projects.CreateProjectScreen

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

fun NavGraphBuilder.addAcademyGraph(navController: NavController) {
    composable(NavigationRoute.Academy.route) {
        AcademyScreen(navController)
    }
}

fun NavGraphBuilder.addCameraGraph(navController: NavController) {
    composable(NavigationRoute.Camera.route) {
        CameraScreen(navController)
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
fun NavGraphBuilder.addBudgetGraph(navController: NavController) {
    // Pantalla de creación de presupuesto (Paso 1)
    composable(NavigationRoute.CreateBudget.route) {
        BudgetCreationScreen(navController = navController)
    }
    // Pantalla de entrada de materiales (Paso 2)
    composable(
        route = NavigationRoute.MaterialEntry.route, // "material_entry/{budgetId}"
        arguments = listOf(navArgument("budgetId") { type = NavType.StringType })
    ) { backStackEntry ->
        val budgetId = backStackEntry.arguments?.getString("budgetId") ?: ""
        MaterialEntryScreen(navController = navController, budgetId = budgetId)
    }
    // Pantalla final del presupuesto (Paso 3)
    composable(
        route = "${NavigationRoute.FinalizeBudget.route}/{budgetId}", // "finalize_budget/{budgetId}"
        arguments = listOf(navArgument("budgetId") { type = NavType.StringType })
    ) { backStackEntry ->
        val budgetId = backStackEntry.arguments?.getString("budgetId") ?: ""
        BudgetFinalizeScreen(navController = navController, budgetId = budgetId)
    }

    // Pantalla de edición de presupuesto
    composable(
        route = "${NavigationRoute.EditBudget.route}/{budgetId}",
        arguments = listOf(navArgument("budgetId") { type = NavType.StringType })
    ) { backStackEntry ->
        val budgetId = backStackEntry.arguments?.getString("budgetId") ?: ""
        BudgetEditScreen(navController, budgetId)
    }

    // Pantalla de visualización de presupuesto
    composable(
        route = "${NavigationRoute.ViewBudget.route}/{budgetId}",
        arguments = listOf(navArgument("budgetId") { type = NavType.StringType })
    ) { backStackEntry ->
        val budgetId = backStackEntry.arguments?.getString("budgetId") ?: ""
        BudgetViewScreen(navController, budgetId)
    }


}

fun NavGraphBuilder.addProjectGraph(navController: NavController) {
    // Pantalla de creación de proyecto
    composable(NavigationRoute.CreateProject.route) {
        CreateProjectScreen(navController = navController)
    }
    // Pantalla de detalles adicionales del proyecto
    composable(NavigationRoute.ProjectDetails.route) {
        ProjectDetailsScreen(navController = navController)
    }
}