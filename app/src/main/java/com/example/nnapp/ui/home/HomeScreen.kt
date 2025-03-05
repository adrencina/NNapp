package com.example.nnapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.example.nnapp.utils.theme.MyAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var isDarkTheme by remember { mutableStateOf(false) }

    // Datos de ejemplo para la pestaña de proyectos
    val proyectos = listOf("Proyecto 1", "Proyecto 2", "Proyecto 3", "Proyecto 4", "Proyecto 5")

    // Obtener el BudgetViewModel para trabajar con presupuestos reales
    val budgetViewModel: BudgetViewModel = hiltViewModel()
    val budgets by budgetViewModel.budgets.collectAsState()

    // Ordenar presupuestos para que el último creado aparezca primero
    val sortedBudgets = budgets.sortedByDescending { it.creationDate }

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
                if (selectedTabIndex == 1) { // Mostrar FAB solo en la pestaña "PRESUPUESTOS"
                    FloatingActionButton(
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
                val screenHeight = maxHeight
                val cardSize = screenHeight * 0.25f // Definir tamaño dinámico para las tarjetas de presupuesto

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

                    if (selectedTabIndex == 0) {
                        // Pestaña de Proyectos
                        LazyRowSection(data = proyectos)
                        Spacer(modifier = Modifier.height(8.dp))
                        // Sección en desarrollo para la lista de proyectos
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            LazyColumnSection()
                        }
                    } else {
                        // Pestaña de Presupuestos
                        BudgetLazyRowSection(
                            budgets = sortedBudgets,
                            cardSize = cardSize,
                            onEdit = { budget ->
                                // Navegar a pantalla de edición (por implementar)
                                navController.navigate("edit_budget/${budget.id}")
                            },
                            onView = { budget ->
                                // Navegar a pantalla de detalle del presupuesto (por implementar)
                                navController.navigate("view_budget/${budget.id}")
                            },
                            onDelete = { budget ->
                                // Eliminar presupuesto mediante el ViewModel
                                budgetViewModel.deleteBudget(budget.id)
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Sección en desarrollo para contenido adicional en LazyColumn (opcional)
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

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    val fakeNavController = rememberNavController()
    HomeScreen(navController = fakeNavController)
}