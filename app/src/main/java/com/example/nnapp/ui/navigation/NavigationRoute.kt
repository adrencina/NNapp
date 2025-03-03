package com.example.nnapp.ui.navigation

sealed class NavigationRoute(val route: String) {
    data object Splash : NavigationRoute("splash")
    data object Auth : NavigationRoute("auth")
    data object Home : NavigationRoute("home")
    data object Camera : NavigationRoute("camera")
    data object Chat : NavigationRoute("chat")
    data object Settings : NavigationRoute("settings")
    data object Projects : NavigationRoute("projects")

    data object BudgetBoard : NavigationRoute("budgetBoard")

    // Rutas con parámetros opcionales
    data object BudgetForm : NavigationRoute("budgetForm?budgetId={budgetId}") {
        fun createRoute(budgetId: String? = null): String {
            return budgetId?.let { "budgetForm?budgetId=$it" } ?: "budgetForm"
        }
    }
}