//package com.example.nnapp.ui.budget.creation
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.automirrored.filled.ArrowForward
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.nnapp.ui.components.InputField
//import com.example.nnapp.ui.components.ProgressBar
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CustomerDataScreen(
//    step: Int,
//    onPrevious: () -> Unit,
//    onNext: () -> Unit
//) {
//    var name by remember { mutableStateOf("") }
//    var address by remember { mutableStateOf("") }
//    var dni by remember { mutableStateOf("") }
//    var phone by remember { mutableStateOf("") }
//    var isFinalConsumer by remember { mutableStateOf(false) }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("DATOS DEL CLIENTE", color = Color.Black) },
//                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
//            )
//        },
//        bottomBar = {
//            Column(
//                modifier = Modifier.fillMaxWidth()
//                    .padding(PaddingValues(start = 16.dp, end = 16.dp)),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) { // Agrupar NavigationBar y ProgressBar
//                Spacer(modifier = Modifier.height(8.dp))
//                ProgressBar(step = step) // Indicador de progreso sobre la NavigationBar
//                Spacer(modifier = Modifier.height(8.dp))
//                NavigationBar {
//                    IconButton(onClick = onPrevious, modifier = Modifier.weight(1f)) {
//                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
//                    }
//                    Spacer(modifier = Modifier.weight(2f))
//                    IconButton(onClick = onNext, modifier = Modifier.weight(1f)) {
//                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Continuar")
//                    }
//                }
//            }
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(horizontal = 16.dp),
//            verticalArrangement = Arrangement.Top,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                "Ingrese los datos del cliente o seleccione consumidor final para continuar.",
//                textAlign = TextAlign.Center,
//                fontSize = 14.sp,
//                color = Color.Gray,
//                modifier = Modifier.padding(bottom = 16.dp)
//            )
//
//            InputField(label = "Nombre del Cliente", value = name) { name = it }
//            Spacer(modifier = Modifier.height(8.dp))
//            InputField(label = "Dirección", value = address) { address = it }
//            Spacer(modifier = Modifier.height(8.dp))
//            InputField(label = "DNI", value = dni, keyboardType = KeyboardType.Number) { dni = it }
//            Spacer(modifier = Modifier.height(8.dp))
//            InputField(
//                label = "Teléfono",
//                value = phone,
//                keyboardType = KeyboardType.Phone
//            ) { phone = it }
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Checkbox(checked = isFinalConsumer, onCheckedChange = { isFinalConsumer = it })
//                Text("CONSUMIDOR FINAL", fontSize = 14.sp, modifier = Modifier.padding(start = 8.dp))
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewCustomerDataScreen() {
//    CustomerDataScreen(
//        step = 1,
//        onPrevious = {},
//        onNext = {}
//    )
//}