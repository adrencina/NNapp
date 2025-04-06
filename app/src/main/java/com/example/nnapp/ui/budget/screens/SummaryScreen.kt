package com.example.nnapp.ui.budget.screens

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nnapp.ui.components.InputField
import com.example.nnapp.ui.components.ProgressBar
import com.example.nnapp.ui.budget.viewmodel.BudgetViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(
    navController: NavController,
    viewModel: BudgetViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var pdfPath by remember { mutableStateOf<Uri?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("RESUMEN Y CONFIRMACIÓN", color = Color.Black) },
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
                    IconButton(
                        onClick = {
                            viewModel.prevStep()
                            navController.navigate("materials")
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                    IconButton(
                        onClick = {
                            scope.launch {
                                pdfPath = viewModel.generatePdf(context)
                                // Aquí se podría llamar a createBudget() para guardar el presupuesto en Firebase
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Finalizar")
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Revise los datos y configure los detalles finales:", fontSize = 14.sp, color = Color.Gray)
            }
            item { Text("Cliente: ${viewModel.clientName.value}", fontSize = 14.sp) }
            item { Text("Dirección: ${viewModel.address.value}", fontSize = 14.sp) }
            item { Text("DNI: ${viewModel.dni.value}", fontSize = 14.sp) }
            item { Text("Teléfono: ${viewModel.phone.value}", fontSize = 14.sp) }
            item { Text("Materiales:", fontSize = 14.sp) }
            itemsIndexed(viewModel.materials.value) { _, material ->
                Text("${material.name}: ${material.quantity} x $${material.unitPrice} = $${material.totalPrice}", fontSize = 14.sp)
            }
            item {
                InputField("Forma de Pago", viewModel.paymentMethod.value) {
                    viewModel.paymentMethod.value = it
                }
            }
            item {
                InputField("Fecha de Validez de la Oferta", viewModel.offerValidityDate.value) {
                    viewModel.offerValidityDate.value = it
                }
            }
            if (pdfPath != null) {
                item {
                    Text("PDF generado en: ${pdfPath.toString()}", fontSize = 14.sp, color = Color.Green)
                }
                item {
                    Button(onClick = { viewModel.sharePdf(pdfPath.toString()) }) {
                        Text("Compartir PDF")
                    }
                }
            }
        }
    }
}