package com.example.nnapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
                    title = { Text("Inicio") },
                    actions = {
                        ToggleThemeButton(isDarkTheme) { isDarkTheme = !isDarkTheme }
                    }
                )
            },
            bottomBar = { BottomNavigationBar() }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                Text(
                    text = "Hola Pepe!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )

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

                // Contenido según la pestaña seleccionada
                when (selectedTabIndex) {
                    0 -> ProyectosContent()
                    1 -> PresupuestosContent()
                }

                Spacer(modifier = Modifier.height(16.dp))
                LogoutButton(navController)
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
fun BottomNavigationBar() {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.home_icon), contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = true,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.book_icon), contentDescription = "Catálogo") },
            label = { Text("Catálogo") },
            selected = false,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.camera_icon), contentDescription = "Cámara") },
            label = { Text("Cámara") },
            selected = false,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.chat_icon), contentDescription = "Chat") },
            label = { Text("Chat") },
            selected = false,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.settings_icon), contentDescription = "Ajustes") },
            label = { Text("Ajustes") },
            selected = false,
            onClick = { }
        )
    }
}

@Composable
fun LogoutButton(navController: NavController) {
    Button(onClick = {
        FirebaseAuth.getInstance().signOut()
        navController.navigate("auth") {
            popUpTo("home") { inclusive = true }
        }
    }) {
        Text("Cerrar Sesión")
    }
}

@Composable
fun ProyectosContent() {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(5) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Text(
                    text = "Proyecto ${it + 1}",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun PresupuestosContent() {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(5) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Text(
                    text = "Presupuesto ${it + 1}",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val fakeNavController = rememberNavController()
    HomeScreen(navController = fakeNavController)
}
