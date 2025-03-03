package com.example.nnapp.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.nnapp.ui.navigation.NavigationRoute

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationRoute.Home to Icons.Filled.Home,
        NavigationRoute.Projects to Icons.Filled.Work,
        NavigationRoute.Camera to Icons.Filled.Camera,
        NavigationRoute.Chat to Icons.AutoMirrored.Filled.Chat,
        NavigationRoute.Settings to Icons.Filled.Settings
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        items.forEach { (route, iconRes) ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = iconRes,
                        contentDescription = route.route,
                        modifier = Modifier.size(28.dp)
                    )
                },
                selected = currentRoute == route.route,
                onClick = {
                    if (currentRoute != route.route) {
                        navController.navigate(route.route) {
                            popUpTo(NavigationRoute.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}