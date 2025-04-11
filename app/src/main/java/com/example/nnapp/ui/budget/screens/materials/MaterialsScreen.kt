package com.example.nnapp.ui.budget.screens.materials

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nnapp.ui.budget.viewmodel.BudgetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialsScreen(
    navController: NavController,
    viewModel: BudgetViewModel
) {
    val materials = viewModel.materials.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CARGA DE MATERIALES", color = Color.Black) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            MaterialBottomBar(
                subtotal = materials.sumOf { it.totalPrice },
                currentStep = viewModel.currentStep.intValue,
                onPrev = {
                    viewModel.prevStep()
                    navController.navigate("client_data")
                },
                onNext = {
                    if (viewModel.isValidStep()) {
                        viewModel.nextStep()
                        navController.navigate("summary")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Ingrese los materiales para el presupuesto:", fontSize = 14.sp, color = Color.Gray)

            MaterialForm(viewModel = viewModel)
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            MaterialList(materials = materials)
        }
    }
}








//
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.automirrored.filled.ArrowForward
//import androidx.compose.material3.*
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.nnapp.data.model.Material
//import com.example.nnapp.ui.budget.viewmodel.BudgetViewModel
//import com.example.nnapp.ui.components.ProgressBar
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MaterialsScreen(
//    navController: NavController,
//    viewModel: BudgetViewModel
//) {
//    var materialName by remember { mutableStateOf("") }
//    var quantity by remember { mutableStateOf("") }
//    var unitPrice by remember { mutableStateOf("") }
//
//    val materials = viewModel.materials.collectAsState().value
//
//    val subtotal = remember(materials) {
//        materials.sumOf { it.totalPrice.toDouble() }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("CARGA DE MATERIALES", color = Color.Black) },
//                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
//            )
//        },
//        bottomBar = {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text("Subtotal: $${"%.2f".format(subtotal)}", fontSize = 16.sp, color = Color.Gray)
//                Spacer(modifier = Modifier.height(8.dp))
//                ProgressBar(step = viewModel.currentStep.intValue, totalSteps = 3)
//                Spacer(modifier = Modifier.height(8.dp))
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    IconButton(
//                        onClick = {
//                            viewModel.prevStep()
//                            navController.navigate("client_data")
//                        },
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
//                    }
//                    IconButton(
//                        onClick = {
//                            if (viewModel.isValidStep()) {
//                                viewModel.nextStep()
//                                navController.navigate("summary")
//                            }
//                        },
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Siguiente")
//                    }
//                }
//            }
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(horizontal = 16.dp, vertical = 1.dp ),
//            verticalArrangement = Arrangement.spacedBy(4.dp)
//        ) {
//            Text("Ingrese los materiales para el presupuesto:", fontSize = 14.sp, color = Color.Gray)
//
//            OutlinedTextField(
//                value = materialName,
//                onValueChange = { materialName = it },
//                label = { Text("Nombre del Material") },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            OutlinedTextField(
//                value = quantity,
//                onValueChange = { quantity = it },
//                label = { Text("Cantidad") },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            OutlinedTextField(
//                value = unitPrice,
//                onValueChange = { unitPrice = it },
//                label = { Text("Precio Unitario") },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Button(
//                onClick = {
//                    if (materialName.isNotBlank() && quantity.isNotBlank() && unitPrice.isNotBlank()) {
//                        val qty = quantity.toIntOrNull() ?: 0
//                        val price = unitPrice.toDoubleOrNull() ?: 0.0
//                        val material = Material(
//                            code = System.currentTimeMillis().toString(),
//                            name = materialName,
//                            description = "",
//                            brand = "",
//                            quantity = qty,
//                            unitPrice = price,
//                            totalPrice = qty * price
//                        )
//                        viewModel.addMaterial(material)
//                        materialName = ""
//                        quantity = ""
//                        unitPrice = ""
//                    }
//                },
//                modifier = Modifier.align(Alignment.End)
//            ) {
//                Text(
//                    text ="Agregar",
//                    fontSize = 14.sp,
//                    modifier = Modifier.padding(1.dp)
//
//                )
//            }
//
//            Spacer(modifier = Modifier.height(1.dp))
//
//            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
//
//            // Encabezado de la tabla
//            Text("Material    |  Cant.  |  Precio Unit.  |    Total",
//                    fontSize = 14.sp,
//                    color = Color.Black,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(4.dp))
//
//            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
//
//            Spacer(modifier = Modifier.height(1.dp))
//
//            // Contenido de la tabla
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight()
//                    .padding(top = 2.dp),
//                verticalArrangement = Arrangement.spacedBy(4.dp)
//            ) {
//                items(materials) { material ->
//                    Surface(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(IntrinsicSize.Min)
//                            .border(
//                                width = 1.dp,
//                                color = Color.LightGray
//                            )
//                            .padding(horizontal = 4.dp, vertical = 8.dp),
//                        color = Color.White,
//                        tonalElevation = 0.dp,
//                        shadowElevation = 0.dp
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth(),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Text(
//                                text = material.name,
//                                modifier = Modifier.weight(2f),
//                                fontSize = 14.sp,
//                                color = Color.Black,
//                                textAlign = TextAlign.Start
//                            )
//                            Text(
//                                text = "${material.quantity}",
//                                modifier = Modifier.weight(1f),
//                                fontSize = 14.sp,
//                                textAlign = TextAlign.Center,
//                                color = Color.Black
//                            )
//                            Text(
//                                text = "$${"%.2f".format(material.unitPrice)}",
//                                modifier = Modifier.weight(2f),
//                                fontSize = 14.sp,
//                                textAlign = TextAlign.Center,
//                                color = Color.Black
//                            )
//                            Text(
//                                text = "$${"%.2f".format(material.totalPrice)}",
//                                modifier = Modifier.weight(2f),
//                                fontSize = 14.sp,
//                                textAlign = TextAlign.End,
//                                color = Color.Black
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}