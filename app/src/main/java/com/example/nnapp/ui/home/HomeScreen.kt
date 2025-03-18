package com.example.nnapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nnapp.ui.components.BottomNavigationBar
import com.example.nnapp.ui.components.FloatingActionButton
import com.example.nnapp.ui.components.TopBar
import com.example.nnapp.ui.home.sections.BudgetLazyRowSection
import com.example.nnapp.ui.home.sections.LazyRowSection
import com.example.nnapp.ui.home.sections.LazyColumnSection
import com.example.nnapp.ui.viewmodel.BudgetViewModel
import com.example.nnapp.ui.viewmodel.ThemeViewModel
import com.example.nnapp.utils.theme.ElectricGrayLight
import com.example.nnapp.utils.theme.NNappTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController,
               themeViewModel: ThemeViewModel = hiltViewModel(),
               budgetViewModel: BudgetViewModel = hiltViewModel()
) {
    // Estado para la pestaña seleccionada
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    // Obtener el estado del tema (claro/oscuro) desde el ThemeViewModel
    val isDarkTheme by themeViewModel.darkMode.collectAsState(initial = false)

    // Datos de ejemplo para la pestaña "PROYECTOS"
    val proyectos = listOf("Proyecto 1", "Proyecto 2", "Proyecto 3", "Proyecto 4", "Proyecto 5")

    // Obtener presupuestos reales del BudgetViewModel y ordenarlos
    val budgets by budgetViewModel.budgets.collectAsState()
    val sortedBudgets = budgets.sortedByDescending { it.creationDate }

    // Aplicar el tema de la app (NNappTheme usa nuestra paleta y tipografía)
    NNappTheme(darkTheme = isDarkTheme) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                // Barra superior con título y botón para cambiar el tema
                TopBar(
                    text = "Hola Adrián!",
                    navController = navController,
                    onThemeToggle = { themeViewModel.toggleTheme() }
                )
            },
            bottomBar = { BottomNavigationBar(navController) },
            floatingActionButton = @androidx.compose.runtime.Composable {
                when (selectedTabIndex) {
                    0 -> FloatingActionButton( // FAB para proyectos
                        icon = Icons.Default.Add,
                        onClick = { navController.navigate("create_project") }
                    )
                    1 -> FloatingActionButton( // FAB para presupuestos
                        icon = Icons.Default.Add,
                        onClick = { navController.navigate("create_budget") }
                    )
                }
            }
        ) { paddingValues ->
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Tamaño dinámico de las tarjetas en base a la altura de la pantalla
                val screenHeight = maxHeight
                val cardSize = screenHeight * 0.25f

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    // Pestañas: "PROYECTOS" y "PRESUPUESTOS"
                    val tabs = listOf("PROYECTOS", "PRESUPUESTOS")
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = Color.Transparent, // Fondo transparente para ver el fondo del Scaffold
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicator = { tabPositions ->
                            SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = {
                                    Text(
                                        text = title,
                                        // Si está seleccionado se usa el color primario; de lo contrario, ElectricGrayLight (gris claro)
                                        color = if (selectedTabIndex == index)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            ElectricGrayLight,
                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    )
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Mostrar contenido según la pestaña seleccionada
                    when (selectedTabIndex) {
                        0 -> { // Sección de Proyectos
                            LazyRowSection(data = proyectos)
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {
                                LazyColumnSection()
                            }
                        }
                        1 -> { // Sección de Presupuestos
                            BudgetLazyRowSection(
                                budgets = sortedBudgets,
                                cardSize = cardSize,
                                onEdit = { budget -> navController.navigate("edit_budget/${budget.id}") },
                                onView = { budget -> navController.navigate("view_budget/${budget.id}") },
                                onDelete = { budget -> budgetViewModel.deleteBudget(budget.id) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {
                                LazyColumnSection()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    val fakeNavController = rememberNavController()
    HomeScreen(navController = fakeNavController)
}