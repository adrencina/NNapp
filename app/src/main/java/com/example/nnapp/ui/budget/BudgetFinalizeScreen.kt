package com.example.nnapp.ui.budget

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nnapp.ui.components.DatePickerComponent
import com.example.nnapp.ui.components.PdfPreviewDialog
import com.example.nnapp.ui.viewmodel.BudgetFinalizeViewModel
import com.example.nnapp.ui.viewmodel.BudgetViewModel
import com.example.nnapp.utils.pdfutils.sharePdf
import kotlinx.coroutines.launch

/**
 * Pantalla final del presupuesto.
 * Permite configurar opciones finales y genera el PDF utilizando los datos reales (cliente, materiales y opciones configuradas).
 * Ofrece dos botones: uno para guardar el PDF y otro para guardar y compartir.
 */
@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetFinalizeScreen(
    navController: NavController,
    budgetId: String,
    finalizeViewModel: BudgetFinalizeViewModel = hiltViewModel(),
    budgetViewModel: BudgetViewModel = hiltViewModel()
) {
    var showPreview by remember { mutableStateOf(false) }
    val state by finalizeViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Al cargar la pantalla, se obtiene el presupuesto real y se carga en el ViewModel final.
    LaunchedEffect(budgetId) {
        val budget = budgetViewModel.getBudgetById(budgetId)
        if (budget != null) {
            println("📋 Datos del presupuesto cargados: $budget")
            finalizeViewModel.loadBudgetData(budget)
        }
    }

    // Cada vez que los materiales cambien y si el PDF no está generado, se genera.
    LaunchedEffect(state.materials) {
        if (!state.pdfGenerated && state.materials.isNotEmpty()) {
            println("📄 Generando PDF con materiales: ${state.materials}")
            finalizeViewModel.generateAndSavePdf(context)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Finalizar Presupuesto", fontSize = 18.sp) },
                actions = {
                    TextButton(onClick = { showPreview = true }) {
                        Text("Ver presupuesto")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Opción: Fecha de validez
                CheckboxOption("Fecha de validez", state.hasExpiryDate) { finalizeViewModel.toggleExpiryDate(it) }
                if (state.hasExpiryDate) {
                    DatePickerComponent { finalizeViewModel.setExpiryDate(it) }
                }

                // Opción: Formas de Pago
                CheckboxOption("Formas de Pago", state.hasPaymentMethods) { finalizeViewModel.togglePaymentMethods(it) }
                if (state.hasPaymentMethods) {
                    PaymentOptionsSelector { methods -> finalizeViewModel.setPaymentMethods(methods) }
                }

                // Opción: Comentario
                CheckboxOption("Agregar comentario", state.hasComment) { finalizeViewModel.toggleComment(it) }
                if (state.hasComment) {
                    BasicTextField(
                        value = state.comment,
                        onValueChange = { finalizeViewModel.setComment(it) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón "Guardar PDF": Solo guarda y muestra un Toast de confirmación.
                Button(
                    onClick = {
                        finalizeViewModel.generateAndSavePdf(context)
                        val pdfUri = state.pdfUri
                        if (pdfUri != null && pdfUri.path != null) {
                            Toast.makeText(context, "PDF guardado en Descargas", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "El PDF no ha sido generado aún", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar PDF", fontSize = 14.sp)
                }

                // Botón "Guardar y compartir PDF": Guarda (si es necesario) y comparte el PDF.
                Button(
                    onClick = {
                        coroutineScope.launch {
                            finalizeViewModel.saveAndSharePdf(context) { uri ->
                                if (uri != null) {
                                    sharePdf(context, uri)
                                } else {
                                    Toast.makeText(context, "Error al generar el PDF. Verifica los datos o permisos.", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar y compartir PDF", fontSize = 14.sp)
                }
            }
        }
    )

    if (showPreview) {
        PdfPreviewDialog(onDismiss = { showPreview = false }, pdfUri = state.pdfUri)
    }
}

@Composable
fun CheckboxOption(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun PaymentOptionsSelector(onSelected: (List<String>) -> Unit) {
    val options = listOf("Efectivo", "Transferencia", "Tarjeta 3 cuotas", "Tarjeta 6 cuotas")
    var selected by remember { mutableStateOf(emptyList<String>()) }
    Column {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selected.contains(option),
                    onCheckedChange = { isChecked ->
                        selected = if (isChecked) selected + option else selected - option
                        onSelected(selected)
                    }
                )
                Text(option)
            }
        }
    }
}