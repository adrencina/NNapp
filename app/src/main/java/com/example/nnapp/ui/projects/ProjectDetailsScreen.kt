package com.example.nnapp.ui.projects

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nnapp.ui.components.ProjectInputField
import com.example.nnapp.utils.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailsScreen(navController: NavController) {
    var description by remember { mutableStateOf("") }
    var projectState by remember { mutableStateOf("") }
    var materialList by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Detalles del Proyecto", style = AppTypography.titleLarge) }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            ProjectInputField(label = "Descripción", value = description, onValueChange = { description = it })
            ProjectInputField(label = "Estado del Proyecto", value = projectState, onValueChange = { projectState = it })
            ProjectInputField(label = "Lista de Materiales", value = materialList, onValueChange = { materialList = it })

            Spacer(modifier = Modifier.height(24.dp))

            // Botón para confirmar detalles
            Button(
                onClick = { /* Lógica de guardado */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Guardar Detalles")
            }
        }
    }
}