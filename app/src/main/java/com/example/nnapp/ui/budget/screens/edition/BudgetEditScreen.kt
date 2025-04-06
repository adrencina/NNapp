package com.example.nnapp.ui.budget.screens.edition

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetEditScreen(navController: NavController, budgetId: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Presupuesto") }, // Título en español
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Aquí se implementará la edición del presupuesto (por implementar)
        Text(
            text = "Pantalla de edición para presupuesto: $budgetId",
            modifier = Modifier.padding(paddingValues)
        )
    }
}