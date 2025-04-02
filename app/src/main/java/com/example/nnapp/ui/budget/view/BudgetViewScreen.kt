package com.example.nnapp.ui.budget.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetViewScreen(navController: NavController, budgetId: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ver Presupuesto") }, // Título en español
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Aquí se mostrará el detalle del presupuesto (por implementar)
        Text(
            text = "Pantalla de visualización para presupuesto: $budgetId",
            modifier = Modifier.padding(paddingValues)
        )
    }
}