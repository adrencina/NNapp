package com.example.nnapp.ui.navigation

sealed class NavigationRoute(val route: String) {
    data object Splash : NavigationRoute("splash")
    data object Auth : NavigationRoute("auth")
    data object Home : NavigationRoute("home")
    data object UnconfirmedBudgets : NavigationRoute("unconfirmedBudgets")
    data object BudgetForm : NavigationRoute("budgetForm/{budgetId}") {
        fun createRoute(budgetId: String? = ""): String {
            return "budgetForm/${budgetId ?: ""}"
        }
    }
}