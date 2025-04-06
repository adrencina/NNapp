package com.example.nnapp.ui.budget.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nnapp.ui.budget.screens.ClientDataScreen
import com.example.nnapp.ui.budget.screens.materials.MaterialsScreen
import com.example.nnapp.ui.budget.screens.SummaryScreen
import com.example.nnapp.ui.budget.viewmodel.BudgetViewModel
import com.example.nnapp.ui.navigation.NavigationRoute

@RequiresApi(Build.VERSION_CODES.Q)
fun NavGraphBuilder.addBudgetCreationFlow(navController: NavController) {
    navigation(
        route = NavigationRoute.BudgetFlow.route,
        startDestination = NavigationRoute.ClientData.route
    ) {
        composable(NavigationRoute.ClientData.route) {
            val viewModel: BudgetViewModel = hiltViewModel()
            ClientDataScreen(navController = navController, viewModel = viewModel)
        }
        composable(NavigationRoute.Materials.route) {
            val viewModel: BudgetViewModel = hiltViewModel()
            MaterialsScreen(navController = navController, viewModel = viewModel)
        }
        composable(NavigationRoute.Summary.route) {
            val viewModel: BudgetViewModel = hiltViewModel()
            SummaryScreen(navController = navController, viewModel = viewModel)
        }
    }
}