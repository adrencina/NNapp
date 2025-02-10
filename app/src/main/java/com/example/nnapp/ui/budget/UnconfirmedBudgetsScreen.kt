package com.example.nnapp.ui.budget

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
fun UnconfirmedBudgetsScreen(
    navController: NavHostController,
    viewModel: BudgetViewModel = hiltViewModel()
) {
    val budgets by viewModel.unconfirmedBudgets.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Presupuestos sin Confirmar",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        BudgetList(
            budgets = budgets,
            onDelete = { viewModel.deleteBudget(it) },
            onEdit = { budgetId ->
                Log.d("Navigation", "Navigating to BudgetForm with id: $budgetId")
                navController.navigate(NavigationRoute.BudgetForm.createRoute(budgetId))
            }
        )

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
fun BudgetList(
    budgets: List<Budget>,
    onDelete: (String) -> Unit,
    onEdit: (String) -> Unit
) {
    LazyColumn {
        items(budgets) { budget ->
            BudgetItem(
                budget = budget,
                onDelete = { onDelete(budget.id) },
                onEdit = { onEdit(budget.id) }
            )
        }
    }
}