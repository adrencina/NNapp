package com.example.nnapp.ui.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nnapp.data.model.Budget
import com.example.nnapp.ui.viewmodel.BudgetViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetCreationScreen(navController: NavController) {
    val viewModel: BudgetViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()

    var clientName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    var clientNameError by remember { mutableStateOf<String?>(null) }
    var addressError by remember { mutableStateOf<String?>(null) }
    var dniError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Presupuesto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Ingrese los datos del cliente", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            // Campo Nombre del Cliente
            OutlinedTextField(
                value = clientName,
                onValueChange = {
                    clientName = it
                    // Validar: no debe iniciar con espacios y no estar vacío
                    clientNameError = when {
                        it.isBlank() -> "El nombre no puede estar vacío"
                        it.firstOrNull()?.isWhitespace() == true -> "El nombre no debe iniciar con espacios"
                        else -> null
                    }
                },
                label = { Text("Nombre del Cliente") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )
            if (clientNameError != null) {
                Text(text = clientNameError!!, color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Campo Dirección
            OutlinedTextField(
                value = address,
                onValueChange = {
                    address = it
                    addressError = if (it.isBlank()) "La dirección no puede estar vacía" else null
                },
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth()
            )
            if (addressError != null) {
                Text(text = addressError!!, color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Campo DNI
            OutlinedTextField(
                value = dni,
                onValueChange = {
                    dni = it
                    dniError = if (it.isBlank()) "El DNI no puede estar vacío" else null
                },
                label = { Text("DNI") },
                modifier = Modifier.fillMaxWidth()
            )
            if (dniError != null) {
                Text(text = dniError!!, color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Campo Teléfono
            OutlinedTextField(
                value = phone,
                onValueChange = {
                    phone = it
                    phoneError = if (it.isBlank()) "El teléfono no puede estar vacío" else null
                },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )
            if (phoneError != null) {
                Text(text = phoneError!!, color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // Validación final antes de guardar
                    val trimmedClientName = clientName.trim()
                    if (trimmedClientName.isEmpty() || clientName != trimmedClientName) {
                        clientNameError = "El nombre no debe iniciar o terminar con espacios"
                        return@Button
                    }
                    if (address.isBlank() || dni.isBlank() || phone.isBlank()) {
                        // Asumir que cada campo muestra su error individualmente
                        return@Button
                    }
                    // Generar fecha actual
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                    val currentDate = dateFormat.format(Date())
                    // Crear nuevo presupuesto
                    val newBudget = Budget(
                        id = "",
                        clientName = trimmedClientName,
                        address = address.trim(),
                        dni = dni.trim(),
                        phone = phone.trim(),
                        materials = emptyList(),
                        confirmed = false,
                        creationDate = currentDate
                    )
                    scope.launch {
                        viewModel.saveBudgetSuspend(newBudget)
                        navController.popBackStack() // Volver a la pantalla principal
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Presupuesto")
            }
        }
    }
}