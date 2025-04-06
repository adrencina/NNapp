//package com.example.nnapp.ui.budget.creation
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.automirrored.filled.ArrowForward
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Checkbox
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.window.Dialog
//
//@Composable
//fun CustomerDialog(
//    onDismiss: () -> Unit
//) {
//    var customerName by remember { mutableStateOf("") }
//    var address by remember { mutableStateOf("") }
//    var dni by remember { mutableStateOf("") }
//    var phone by remember { mutableStateOf("") }
//    var isFinalConsumer by remember { mutableStateOf(false) }
//
//    // Función para cargar datos estándar cuando se selecciona el checkbox
//    fun loadDefaultCustomer() {
//        if (isFinalConsumer) {
//            customerName = "Consumidor Final"
//            address = "No disponible"
//            dni = "00000000"
//            phone = "0000000000"
//        } else {
//            customerName = ""
//            address = ""
//            dni = ""
//            phone = ""
//        }
//    }
//
//    Dialog(onDismissRequest = onDismiss) {
//        Surface(
//            modifier = Modifier.fillMaxWidth(),
//            shape = MaterialTheme.shapes.large,
//            tonalElevation = 8.dp
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                Text(
//                    text = "CREAR PRESUPUESTO",
//                    style = MaterialTheme.typography.titleLarge
//                )
//                Text(
//                    text = "INGRESE LOS DATOS DEL CLIENTE",
//                    style = MaterialTheme.typography.bodyMedium
//                )
//
//                // Campos de entrada
//                OutlinedTextField(
//                    value = customerName,
//                    onValueChange = { customerName = it },
//                    label = { Text("Nombre del Cliente") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                OutlinedTextField(
//                    value = address,
//                    onValueChange = { address = it },
//                    label = { Text("Dirección") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                OutlinedTextField(
//                    value = dni,
//                    onValueChange = { dni = it },
//                    label = { Text("DNI") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                OutlinedTextField(
//                    value = phone,
//                    onValueChange = { phone = it },
//                    label = { Text("Teléfono") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                // Checkbox para cargar datos estándar
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Checkbox(
//                        checked = isFinalConsumer,
//                        onCheckedChange = {
//                            isFinalConsumer = it
//                            loadDefaultCustomer()
//                        }
//                    )
//                    Text(text = "CONSUMIDOR FINAL")
//                }
//
//                HorizontalDivider()
//
//                // Sección de vista previa en tiempo real
//                Card(
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEDEDED))
//                ) {
//                    Column(
//                        modifier = Modifier.padding(16.dp)
//                    ) {
//                        Text("DATOS INGRESADOS:", style = MaterialTheme.typography.titleMedium)
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Row {
//                            Text("NOMBRE: $customerName", modifier = Modifier.weight(1f))
//                            Text("DIR.: $address", modifier = Modifier.weight(1f))
//                        }
//                        Row {
//                            Text("DNI: $dni", modifier = Modifier.weight(1f))
//                            Text("TEL: $phone", modifier = Modifier.weight(1f))
//                        }
//                    }
//                }
//
//                // Botones de navegación
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    IconButton(onClick = onDismiss) {
//                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
//                    }
//                    IconButton(onClick = { /* Acción siguiente */ }) {
//                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Siguiente")
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun CustomerDialogPreview() {
//    CustomerDialog(onDismiss = {})
//}