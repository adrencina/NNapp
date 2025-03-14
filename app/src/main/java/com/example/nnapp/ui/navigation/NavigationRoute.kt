package com.example.nnapp.ui.navigation

sealed class NavigationRoute(val route: String) {
    data object Splash : NavigationRoute("splash")
    data object Auth : NavigationRoute("auth")
    data object Home : NavigationRoute("home")
    data object Camera : NavigationRoute("camera")
    data object Chat : NavigationRoute("chat")
    data object Settings : NavigationRoute("settings")
    data object Projects : NavigationRoute("projects")

    data object CreateBudget : NavigationRoute("create_budget")
    data object MaterialEntry : NavigationRoute("material_entry/{budgetId}")
    data object EditBudget : NavigationRoute("edit_budget")
    data object ViewBudget : NavigationRoute("view_budget")
    data object FinalizeBudget : NavigationRoute("finalize_budget")
}
