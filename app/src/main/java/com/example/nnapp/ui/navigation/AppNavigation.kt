package com.example.nnapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nnapp.ui.auth.AuthScreen
import com.example.nnapp.ui.budget.BudgetScreen
import com.example.nnapp.ui.home.HomeScreen
import com.example.nnapp.ui.splash.SplashScreen
import com.example.nnapp.ui.viewmodel.AuthViewModel
import com.example.nnapp.ui.viewmodel.BudgetViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("auth") {
            val authViewModel: AuthViewModel = hiltViewModel()
            AuthScreen(viewModel = authViewModel, navController = navController)
        }
        composable("home") { HomeScreen(navController) }
        composable("budgets") {
            val budgetViewModel: BudgetViewModel = hiltViewModel()
            BudgetScreen(viewModel = budgetViewModel, navController = navController)
        }

    }
}