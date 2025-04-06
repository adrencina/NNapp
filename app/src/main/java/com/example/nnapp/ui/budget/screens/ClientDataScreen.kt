package com.example.nnapp.ui.budget.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nnapp.ui.components.InputField
import com.example.nnapp.ui.components.ProgressBar
import com.example.nnapp.ui.budget.viewmodel.BudgetViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientDataScreen(
    navController: NavController,
    viewModel: BudgetViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("DATOS DEL CLIENTE", color = Color.Black) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                ProgressBar(step = viewModel.currentStep.intValue, totalSteps = 3)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // No se muestra botón "Atrás" en la primera pantalla
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            if (viewModel.isValidStep()) {
                                viewModel.nextStep()
                                navController.navigate("materials")
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Siguiente")
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 2.dp ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    "Ingrese los datos del cliente o seleccione consumidor final para continuar.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            item {
                InputField("Nombre del Cliente", viewModel.clientName.value) {
                    viewModel.clientName.value = it
                }
            }
            item {
                InputField("Dirección", viewModel.address.value) {
                    viewModel.address.value = it
                }
            }
            item {
                InputField("DNI", viewModel.dni.value, keyboardType = KeyboardType.Number) {
                    viewModel.dni.value = it
                }
            }
            item {
                InputField("Teléfono", viewModel.phone.value, keyboardType = KeyboardType.Phone) {
                    viewModel.phone.value = it
                }
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = viewModel.isFinalConsumer.value,
                        onCheckedChange = {
                            viewModel.isFinalConsumer.value = it
                            if (it) {
                                viewModel.clientName.value = "Consumidor Final"
                                viewModel.address.value = "No disponible"
                                viewModel.dni.value = "00000000"
                                viewModel.phone.value = "0000000000"
                            }
                        }
                    )
                    Text("CONSUMIDOR FINAL", fontSize = 14.sp, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}