package com.example.nnapp.ui.projects

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nnapp.ui.components.ProjectInputField
import com.example.nnapp.utils.theme.AppTypography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProjectScreen(navController: NavController) {
    var projectName by remember { mutableStateOf("") }
    var clientName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Nuevo Proyecto", style = AppTypography.titleLarge) }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Campos básicos del formulario
            ProjectInputField(label = "Nombre del Proyecto", value = projectName, onValueChange = { projectName = it })
            ProjectInputField(label = "Nombre del Cliente", value = clientName, onValueChange = { clientName = it })
            ProjectInputField(label = "Dirección", value = address, onValueChange = { address = it })
            ProjectInputField(label = "DNI", value = dni, onValueChange = { dni = it }, keyboardType = KeyboardType.Number)
            ProjectInputField(label = "Teléfono", value = phone, onValueChange = { phone = it }, keyboardType = KeyboardType.Phone)
            ProjectInputField(label = "Fecha de Inicio", value = startDate, onValueChange = { startDate = it })
            ProjectInputField(label = "Fecha de Finalización", value = endDate, onValueChange = { endDate = it })

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para expandir detalles adicionales
            OutlinedButton(
                onClick = { navController.navigate("project_details") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Agregar más detalles")
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Expandir")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de crear proyecto
            Button(
                onClick = { /* Lógica de creación */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Crear Proyecto")
            }
        }
    }
}