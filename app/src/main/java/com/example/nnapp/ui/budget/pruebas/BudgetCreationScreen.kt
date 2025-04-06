//package com.example.nnapp.ui.budget.creation
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.automirrored.filled.ArrowForward
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.KeyboardCapitalization
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavController
//import com.example.nnapp.ui.components.ProgressBar
//import com.example.nnapp.ui.viewmodel.ClientDataViewModel
//
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BudgetCreationScreen(
//    navController: NavController,
//    viewModel: ClientDataViewModel = hiltViewModel()
//) {
//    val scope = rememberCoroutineScope()
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
//                modifier = Modifier.fillMaxWidth().padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Spacer(modifier = Modifier.height(8.dp))
//                ProgressBar(step = viewModel.currentStep)
//                Spacer(modifier = Modifier.height(8.dp))
//                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                    IconButton(onClick = { viewModel.prevStep() }) {
//                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
//                    }
//                    IconButton(
//                        onClick = {
//                            if (viewModel.isValidStep()) {
//                                viewModel.nextStep()
//                                if (viewModel.currentStep > 3) {
//                                    navController.navigate("summary_screen")
//                                }
//                            }
//                        }
//                    ) {
//                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Siguiente")
//                    }
//                }
//            }
//        }
//    ) { paddingValues ->
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            item {
//                Text(
//                    "Ingrese los datos del cliente o seleccione consumidor final para continuar.",
//                    fontSize = 14.sp,
//                    color = Color.Gray
//                )
//            }
//            item { InputField("Nombre del Cliente", viewModel.clientName) { viewModel.clientName = it } }
//            item { InputField("Dirección", viewModel.address) { viewModel.address = it } }
//            item { InputField("DNI", viewModel.dni, KeyboardType.Number) { viewModel.dni = it } }
//            item { InputField("Teléfono", viewModel.phone, KeyboardType.Phone) { viewModel.phone = it } }
//            item {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Checkbox(
//                        checked = viewModel.isFinalConsumer,
//                        onCheckedChange = {
//                            viewModel.isFinalConsumer = it
//                            if (it) {
//                                viewModel.clientName = "Consumidor Final"
//                                viewModel.address = "No disponible"
//                                viewModel.dni = "00000000"
//                                viewModel.phone = "0000000000"
//                            }
//                        }
//                    )
//                    Text("CONSUMIDOR FINAL", fontSize = 14.sp, modifier = Modifier.padding(start = 8.dp))
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun InputField(
//    label: String,
//    value: String,
//    keyboardType: KeyboardType = KeyboardType.Text,
//    onValueChange: (String) -> Unit
//) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        label = { Text(label) },
//        modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp),
//        keyboardOptions = KeyboardOptions(
//            capitalization = KeyboardCapitalization.Words,
//            keyboardType = keyboardType
//        ),
//        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
//    )
//}