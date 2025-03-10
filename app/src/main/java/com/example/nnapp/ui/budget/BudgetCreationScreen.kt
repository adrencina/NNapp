package com.example.nnapp.ui.budget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nnapp.data.model.Budget
import com.example.nnapp.ui.viewmodel.BudgetViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun BudgetCreationScreen(navController: NavController) {
    val viewModel: BudgetViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()

    // Estados para los datos del cliente
    var customerName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var isFinalConsumer by remember { mutableStateOf(false) }

    // Estados para errores de validación
    var customerNameError by remember { mutableStateOf<String?>(null) }
    var addressError by remember { mutableStateOf<String?>(null) }
    var dniError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }

    // Función para cargar datos estándar si se marca "Consumidor Final"
    fun loadDefaultCustomer() {
        if (isFinalConsumer) {
            customerName = "Consumidor Final"
            address = "No disponible"
            dni = "00000000"
            phone = "0000000000"
        } else {
            customerName = ""
            address = ""
            dni = ""
            phone = ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Presupuesto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Ingrese los datos del cliente", style = MaterialTheme.typography.titleLarge)

            // Campo: Nombre del Cliente (input reducido a 56.dp de alto)
            OutlinedTextField(
                value = customerName,
                onValueChange = {
                    customerName = it
                    customerNameError = when {
                        it.isBlank() -> "El nombre no puede estar vacío"
                        it.firstOrNull()?.isWhitespace() == true -> "No debe iniciar con espacios"
                        else -> null
                    }
                },
                label = { Text("Nombre del Cliente") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )
            if (customerNameError != null) {
                Text(text = customerNameError!!, color = MaterialTheme.colorScheme.error)
            }

            // Campo: Dirección
            OutlinedTextField(
                value = address,
                onValueChange = {
                    address = it
                    addressError = if (it.isBlank()) "La dirección no puede estar vacía" else null
                },
                label = { Text("Dirección") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
            if (addressError != null) {
                Text(text = addressError!!, color = MaterialTheme.colorScheme.error)
            }

            // Campo: DNI
            OutlinedTextField(
                value = dni,
                onValueChange = {
                    dni = it
                    dniError = if (it.isBlank()) "El DNI no puede estar vacío" else null
                },
                label = { Text("DNI") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
            if (dniError != null) {
                Text(text = dniError!!, color = MaterialTheme.colorScheme.error)
            }

            // Campo: Teléfono
            OutlinedTextField(
                value = phone,
                onValueChange = {
                    phone = it
                    phoneError = if (it.isBlank()) "El teléfono no puede estar vacío" else null
                },
                label = { Text("Teléfono") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
            if (phoneError != null) {
                Text(text = phoneError!!, color = MaterialTheme.colorScheme.error)
            }

            // Checkbox para "Consumidor Final"
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isFinalConsumer,
                    onCheckedChange = {
                        isFinalConsumer = it
                        loadDefaultCustomer()
                    }
                )
                Text("CONSUMIDOR FINAL")
            }

            HorizontalDivider()

            // Vista previa en tiempo real de los datos ingresados
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEDEDED))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("DATOS INGRESADOS:", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text("NOMBRE: $customerName", modifier = Modifier.weight(1f))
                        Text("DIR.: $address", modifier = Modifier.weight(1f))
                    }
                    Row {
                        Text("DNI: $dni", modifier = Modifier.weight(1f))
                        Text("TEL: $phone", modifier = Modifier.weight(1f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones de navegación (Back y Siguiente)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
                IconButton(
                    onClick = {
                        // Validaciones: si algún campo está vacío, no continúa
                        if (customerName.isBlank() || address.isBlank() || dni.isBlank() || phone.isBlank()) return@IconButton

                        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                        val currentDate = dateFormat.format(Date())

                        val newBudget = Budget(
                            id = "",
                            clientName = customerName.trim(),
                            address = address.trim(),
                            dni = dni.trim(),
                            phone = phone.trim(),
                            materials = emptyList(),
                            confirmed = false,
                            creationDate = currentDate
                        )

                        scope.launch {
                            val budgetId = viewModel.createBudget(newBudget)
                            if (budgetId.isNotEmpty()) {
                                navController.navigate("material_entry/$budgetId")
                            }
                        }
                    }
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Siguiente")
                }
            }
        }
    }
}