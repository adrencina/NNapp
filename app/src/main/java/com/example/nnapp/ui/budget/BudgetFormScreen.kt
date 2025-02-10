package com.example.nnapp.ui.budget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nnapp.data.model.Budget
import com.example.nnapp.data.model.Material
import com.example.nnapp.ui.viewmodel.BudgetViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BudgetFormScreen(
    budgetId: String? = null,
    navController: NavController,
    viewModel: BudgetViewModel = hiltViewModel()
) {
    var clientName by remember { mutableStateOf("Consumidor Final") }
    var address by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var materialsList by remember { mutableStateOf<List<Material>>(emptyList()) }
    var usePredefinedMaterials by remember { mutableStateOf(false) }

    // Cargar datos si el presupuesto existe
    val budget = produceState<Budget?>(initialValue = null) {
        value = budgetId?.let { viewModel.getBudgetById(it) }
    }.value

    // Inicializar valores solo cuando `budget` esté disponible
    LaunchedEffect(budget) {
        budget?.let {
            clientName = it.clientName
            address = it.address
            dni = it.dni
            phone = it.phone
            materialsList = it.materials
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = if (budgetId == null) "Crear Presupuesto" else "Editar Presupuesto",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = clientName,
            onValueChange = { clientName = it },
            label = { Text("Nombre del Cliente") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = dni,
            onValueChange = { dni = it },
            label = { Text("DNI") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Número de Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val newBudget = Budget(
                    id = budgetId ?: "",
                    clientName = clientName,
                    address = address,
                    dni = dni,
                    phone = phone,
                    materials = materialsList,
                    confirmed = false,
                    creationDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(java.util.Date())
                )
                viewModel.saveBudget(newBudget)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Presupuesto")
        }
    }
}