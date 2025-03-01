package com.example.nnapp.ui.budget

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.nnapp.data.model.Budget
import com.example.nnapp.ui.navigation.NavigationRoute
import com.example.nnapp.ui.viewmodel.BudgetViewModel

@Composable
fun BudgetBoardScreen(
    navController: NavHostController,
    viewModel: BudgetViewModel = hiltViewModel()
) {
    val budgets by viewModel.budgets.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Planilla de Presupuestos",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Lista horizontal de presupuestos
        LazyRow {
            items(budgets) { budget ->
                BudgetItemHorizontal(
                    budget = budget,
                    onEdit = {
                        Log.d("Navigation", "Navigating to BudgetForm with id: ${budget.id}")
                        navController.navigate(NavigationRoute.BudgetForm.createRoute(budget.id))
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                Log.d("Navigation", "Navigating to BudgetForm for new budget")
                navController.navigate(NavigationRoute.BudgetForm.createRoute())
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nuevo Presupuesto")
        }
    }
}

@Composable
fun BudgetItemHorizontal(
    budget: Budget,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(end = 8.dp)
            .width(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = budget.clientName,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Creado: ${budget.creationDate}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onEdit,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Editar")
            }
        }
    }
}