package com.example.nnapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nnapp.R
import com.example.nnapp.utils.theme.MyAppTheme
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var isDarkTheme by remember { mutableStateOf(false) }

    MyAppTheme(darkTheme = isDarkTheme) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Hola Pepe!") },
                    actions = {
                        IconButton(onClick = {
                            FirebaseAuth.getInstance().signOut()
                            navController.navigate("auth") {
                                popUpTo("home") { inclusive = true }
                            }
                        }) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = "Cerrar sesión")
                        }
                        ToggleThemeButton(isDarkTheme) { isDarkTheme = !isDarkTheme }
                    }
                )
            },
            bottomBar = { BottomNavigationBar(navController = navController, selectedRoute = "home") }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                // Pestañas superiores
                val tabs = listOf("PROYECTOS", "PRESUPUESTOS")
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) }
                        )
                    }
                }

                // Contenido dinámico según la pestaña seleccionada
                when (selectedTabIndex) {
                    0 -> ContentSection("Proyecto")
                    1 -> ContentSection("Presupuesto")
                }
            }
        }
    }
}

@Composable
fun ContentSection(label: String) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // LazyRow - 30% de la pantalla
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(3) { index ->
                Card(
                    modifier = Modifier
                        .width(120.dp)
                        .height(100.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("$label ${index + 1}", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }

        // LazyColumn - espacio restante, mostrando 3 elementos visibles
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(3) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("$label ${index + 1}", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}

@Composable
fun ToggleThemeButton(isDarkTheme: Boolean, onToggle: () -> Unit) {
    IconButton(onClick = onToggle) {
        Icon(
            imageVector = if (isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
            contentDescription = "Toggle Theme"
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, selectedRoute: String) {
    val items = listOf(
        "home" to R.drawable.home_icon,
        "projects" to R.drawable.book_icon,
        "camera" to R.drawable.camera_icon,
        "chat" to R.drawable.chat_icon,
        "settings" to R.drawable.settings_icon
    )

    NavigationBar {
        items.forEach { (route, iconRes) ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(iconRes),
                        contentDescription = route,
                        modifier = Modifier.size(25.dp) // Cambia el tamaño del icono
                    )
                },
                selected = selectedRoute == route,
                onClick = {
                    navController.navigate(route) {
                        popUpTo("home") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val fakeNavController = rememberNavController()
    HomeScreen(navController = fakeNavController)
}