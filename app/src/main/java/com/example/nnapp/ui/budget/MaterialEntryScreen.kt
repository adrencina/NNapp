package com.example.nnapp.ui.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nnapp.data.model.Material
import com.example.nnapp.ui.viewmodel.BudgetViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialEntryScreen(
    navController: NavController,
    budgetId: String
) {
    val viewModel: BudgetViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()

    // Estados para campos de entrada
    var materialName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var unitPrice by remember { mutableStateOf("") }

    // Estados para errores de validación
    var materialNameError by remember { mutableStateOf<String?>(null) }
    var quantityError by remember { mutableStateOf<String?>(null) }
    var unitPriceError by remember { mutableStateOf<String?>(null) }

    // Lista de materiales (cargados y agregados)
    var materials by remember { mutableStateOf<List<Material>>(emptyList()) }

    // Cargar los materiales del presupuesto al iniciar
    LaunchedEffect(budgetId) {
        val budget = viewModel.getBudgetById(budgetId)
        materials = budget?.materials ?: emptyList()
    }

    // Calcular subtotal general
    val total = materials.sumOf { it.quantity * it.unitPrice }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CREAR PRESUPUESTO", fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Subtítulo
            Text(
                text = "INGRESE LOS MATERIALES O SERVICIOS",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp)
            )

            // Campo: Nombre del Material
            OutlinedTextField(
                value = materialName,
                onValueChange = { value ->
                    materialName = value
                    materialNameError = when {
                        value.isBlank() -> "El nombre no puede estar vacío"
                        value != value.trim() -> "No debe iniciar ni terminar con espacios"
                        else -> null
                    }
                },
                label = { Text("Nombre del Material") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
            )
            if (materialNameError != null) {
                Text(text = materialNameError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
            }

            // Campo: Cantidad (máximo 5 dígitos)
            OutlinedTextField(
                value = quantity,
                onValueChange = { value ->
                    val filtered = value.filter { it.isDigit() }
                    if (filtered.length <= 5) {
                        quantity = filtered
                    }
                    quantityError = if (filtered.isBlank()) "La cantidad no puede estar vacía" else null
                },
                label = { Text("Cantidad") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                keyboardOptions = KeyboardOptions.Default,
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
            )
            if (quantityError != null) {
                Text(text = quantityError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
            }

            // Campo: Precio Unitario (permitir 1 punto decimal, máximo 7 caracteres)
            OutlinedTextField(
                value = unitPrice,
                onValueChange = { value ->
                    val filtered = value.filter { it.isDigit() || it == '.' }
                    if (filtered.count { it == '.' } <= 1 && filtered.length <= 7) {
                        unitPrice = filtered
                    }
                    unitPriceError = if (filtered.isBlank()) "El precio unitario no puede estar vacío" else null
                },
                label = { Text("Precio Unitario") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                keyboardOptions = KeyboardOptions.Default,
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
            )
            if (unitPriceError != null) {
                Text(text = unitPriceError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
            }

            // Botón "Agregar Material"
            Button(
                onClick = {
                    if (materialName.isBlank() || quantity.isBlank() || unitPrice.isBlank() ||
                        materialName != materialName.trim()
                    ) {
                        // No continuar si hay error de validación
                        return@Button
                    }
                    val newMaterial = Material(
                        name = materialName.trim(),
                        quantity = quantity.toInt(),
                        unitPrice = unitPrice.toDouble()
                    )
                    scope.launch {
                        viewModel.addMaterialToBudget(budgetId, newMaterial)
                        // Agregarlo a la lista local para vista inmediata
                        materials = listOf(newMaterial) + materials
                    }
                    // Limpiar campos
                    materialName = ""
                    quantity = ""
                    unitPrice = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Material", fontSize = 14.sp)
            }

            // Sección "tabla" con encabezados y lista de materiales (mostrar hasta 5 con scroll)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEDEDED))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Encabezados de la tabla
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("DESCRIPCIÓN", modifier = Modifier.weight(1f), fontSize = 12.sp)
                        Text("CANTIDAD", modifier = Modifier.weight(1f), fontSize = 12.sp)
                        Text("PRECIO U.", modifier = Modifier.weight(1f), fontSize = 12.sp)
                    }
                    // Lista de materiales con scroll (mostrar hasta 5)
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(materials.take(5)) { material ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(material.name, modifier = Modifier.weight(1f), fontSize = 12.sp)
                                Text("${material.quantity}", modifier = Modifier.weight(1f), fontSize = 12.sp)
                                Text("$${material.unitPrice}", modifier = Modifier.weight(1f), fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            // Sección "TOTAL"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("TOTAL: ", fontSize = 14.sp)
                Text("$${total}", fontSize = 14.sp)
            }

            // Botones de navegación (flechas)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Atrás"
                    )
                }
                IconButton(onClick = { /* Acción siguiente */ }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Siguiente"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MaterialEntryScreenPreview() {
    MaterialEntryScreen(
        navController = rememberNavController(),
        budgetId = "preview-id"
    )
}