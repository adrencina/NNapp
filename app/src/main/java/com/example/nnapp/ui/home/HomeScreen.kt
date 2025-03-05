package com.example.nnapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nnapp.ui.components.BottomNavigationBar
import com.example.nnapp.ui.components.FloatingActionButton
import com.example.nnapp.ui.components.TopBar
import com.example.nnapp.ui.home.sections.LazyRowSection
import com.example.nnapp.ui.home.sections.LazyColumnSection
import com.example.nnapp.utils.theme.MyAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var isDarkTheme by remember { mutableStateOf(false) }

    val proyectos = listOf("Proyecto 1", "Proyecto 2", "Proyecto 3", "Proyecto 4", "Proyecto 5")
    val presupuestos = listOf("Presupuesto A", "Presupuesto B", "Presupuesto C", "Presupuesto D", "Presupuesto E")

    MyAppTheme(darkTheme = isDarkTheme) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopBar(
                    text = "Hola Adrián!",
                    navController = navController,
                    onThemeToggle = { isDarkTheme = !isDarkTheme }
                )
            },
            bottomBar = { BottomNavigationBar(navController) },
            floatingActionButton = {
                FloatingActionButton(
                    icon = Icons.Default.Add,
                    onClick = { /* Acción al presionar el FAB */ }
                )
            }
        ) { paddingValues ->
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                val screenHeight = maxHeight
                val cardSize = screenHeight * 0.25f // Tamaño cuadrado dinámico basado en el 25% de la pantalla

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
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

                    Spacer(modifier = Modifier.height(16.dp))

                    val data = if (selectedTabIndex == 0) proyectos else presupuestos

                    LazyRowSection(data = data)
                    Spacer(modifier = Modifier.height(8.dp))
                    // Mostramos el mensaje de "En desarrollo"
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                        contentAlignment = androidx.compose.ui.Alignment.Center) {
                        LazyColumnSection()
                    }

                }
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
