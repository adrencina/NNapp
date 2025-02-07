package com.example.nnapp.ui.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nnapp.data.model.Budget
import com.example.nnapp.ui.viewmodel.BudgetViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BudgetScreen(viewModel: BudgetViewModel = hiltViewModel(), navController: NavController) {
    val budgets = viewModel.budgets.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = {
            val newBudget = Budget(
                name = "New Budget",
                creationDate = SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                ).format(java.util.Date()),
                materials = emptyList()
            )
            viewModel.addBudget(newBudget)
        }) {
            Text("Add Budget")
        }

        LazyColumn {
            items(budgets) { budget ->
                BudgetItem(budget = budget, onDelete = { viewModel.deleteBudget(budget.id) })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.popBackStack() }) { // Permite volver a la pantalla anterior
            Text("Volver")
        }
    }
}