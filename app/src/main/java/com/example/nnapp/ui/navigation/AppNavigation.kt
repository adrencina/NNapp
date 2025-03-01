package com.example.nnapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nnapp.ui.auth.AuthScreen
import com.example.nnapp.ui.budget.BudgetBoardScreen
import com.example.nnapp.ui.budget.BudgetFormScreen
import com.example.nnapp.ui.home.HomeScreen
import com.example.nnapp.ui.splash.SplashScreen
import com.example.nnapp.ui.viewmodel.AuthViewModel
import com.example.nnapp.ui.viewmodel.BudgetViewModel

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Splash.route
    ) {
        composable(NavigationRoute.Splash.route) {
            SplashScreen(navController)
        }
        composable(NavigationRoute.Auth.route) {
            val authViewModel: AuthViewModel = hiltViewModel()
            AuthScreen(viewModel = authViewModel, navController = navController)
        }
        composable(NavigationRoute.Home.route) {
            HomeScreen(navController = navController)
        }
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
}