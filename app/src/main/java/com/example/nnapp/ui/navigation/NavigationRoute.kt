package com.example.nnapp.ui.navigation

/**
 * Define todas las rutas de navegación usadas en la aplicación.
 */
sealed class NavigationRoute(val route: String) {
    data object Splash : NavigationRoute("splash")
    data object Auth : NavigationRoute("auth")
    data object Home : NavigationRoute("home")
    data object Academy : NavigationRoute("academy")
    data object Camera : NavigationRoute("camera")
    data object Chat : NavigationRoute("chat")
    data object Settings : NavigationRoute("settings")

    // Rutas relacionadas con presupuestos
    data object CreateBudget : NavigationRoute("create_budget")
    data object MaterialEntry : NavigationRoute("material_entry/{budgetId}")
    data object FinalizeBudget : NavigationRoute("finalize_budget")

    // Rutas para edición y visualización
    data object EditBudget : NavigationRoute("edit_budget")
    data object ViewBudget : NavigationRoute("view_budget")

    // Rutas nuevas para proyectos
    data object CreateProject : NavigationRoute("create_project")
    data object ProjectDetails : NavigationRoute("project_details")

}