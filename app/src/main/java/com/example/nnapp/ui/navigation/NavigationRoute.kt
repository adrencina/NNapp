package com.example.nnapp.ui.navigation

sealed class NavigationRoute(val route: String) {
    data object Splash : NavigationRoute("splash")
    data object Auth : NavigationRoute("auth")
    data object Home : NavigationRoute("home")
    // Usaremos "budgetBoard" para la planilla de presupuestos.
    data object BudgetBoard : NavigationRoute("budgetBoard")
    // Usamos query parameter para que budgetId sea opcional.
    data object BudgetForm : NavigationRoute("budgetForm?budgetId={budgetId}") {
        fun createRoute(budgetId: String? = null): String {
            return if (budgetId.isNullOrEmpty()) {
                "budgetForm"
            } else {
                "budgetForm?budgetId=$budgetId"
            }
        }
    }
}