package com.example.nnapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nnapp.ui.auth.AuthScreen
import com.example.nnapp.ui.home.HomeScreen
import com.example.nnapp.ui.viewmodel.AuthViewModel


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()

    NavHost(navController, startDestination = "auth") {
        composable("auth") { AuthScreen(viewModel = authViewModel, navController = navController) }
        composable("home") { HomeScreen() }
    }
}