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
import com.example.nnapp.ui.home.sections.LazyColumnSection
import com.example.nnapp.ui.home.sections.LazyRowSection
import com.example.nnapp.ui.home.sections.BudgetLazyRowSection
import com.example.nnapp.ui.navigation.NavigationRoute
import com.example.nnapp.ui.budget.viewmodel.BudgetViewModel
import com.example.nnapp.ui.viewmodel.ThemeViewModel
import com.example.nnapp.utils.theme.ElectricGrayLight
import com.example.nnapp.utils.theme.NNappTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    themeViewModel: ThemeViewModel = hiltViewModel(),
    budgetViewModel: BudgetViewModel = hiltViewModel()
) {
    // Estado para la pestaña seleccionada
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    // Estado del tema (claro/oscuro)
    val isDarkTheme by themeViewModel.darkMode.collectAsState(initial = false)

    // Datos de ejemplo para la pestaña "PROYECTOS"
    val proyectos = listOf("Proyecto 1", "Proyecto 2", "Proyecto 3", "Proyecto 4", "Proyecto 5")

    // Se elimina el acceso a 'budgets' ya que no se encuentra definido en BudgetViewModel.
    // Valida que la sección de presupuestos muestre un placeholder o contenido alternativo.
    NNappTheme(darkTheme = isDarkTheme) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopBar(
                    text = "Hola Adrián!",
                    navController = navController,
                    onThemeToggle = { themeViewModel.toggleTheme() }
                )
            },
            bottomBar = { BottomNavigationBar(navController) },
            floatingActionButton = {
                when (selectedTabIndex) {
                    0 -> FloatingActionButton(
                        icon = Icons.Default.Add,
                        onClick = { navController.navigate("create_project") }
                    )
                    1 -> FloatingActionButton(
                        icon = Icons.Default.Add,
                        // Navega a la pantalla de datos del cliente, inicio del flujo de presupuesto
                        onClick = { navController.navigate(NavigationRoute.BudgetFlow.route) }

                    )
                }
            }
        ) { paddingValues ->
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                val screenHeight = this.maxHeight
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
                        containerColor = Color.Transparent,
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
                                        color = if (selectedTabIndex == index)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            ElectricGrayLight,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                    )
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Contenido según la pestaña seleccionada
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
                            // Si en el futuro se implementa la lista de presupuestos, se usará BudgetLazyRowSection
                            BudgetLazyRowSection(
                                budgets = emptyList(), // Actualmente se envía lista vacía
                                cardSize = cardSize,
                                onEdit = { /* Implementar navegación para editar presupuesto */ },
                                onView = { /* Implementar navegación para ver presupuesto */ },
                                onDelete = { /* Implementar eliminación si se requiere */ }
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