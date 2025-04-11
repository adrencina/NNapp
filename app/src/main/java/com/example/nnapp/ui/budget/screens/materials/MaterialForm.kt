package com.example.nnapp.ui.budget.screens.materials

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nnapp.data.model.Material
import com.example.nnapp.ui.budget.viewmodel.BudgetViewModel

@Composable
fun MaterialForm(viewModel: BudgetViewModel) {
    var materialName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var unitPrice by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = materialName,
            onValueChange = { materialName = it },
            label = { Text("Nombre del Material") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Cantidad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = unitPrice,
            onValueChange = { unitPrice = it },
            label = { Text("Precio Unitario") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (materialName.isNotBlank() && quantity.isNotBlank() && unitPrice.isNotBlank()) {
                    val qty = quantity.toIntOrNull() ?: 0
                    val price = unitPrice.toDoubleOrNull() ?: 0.0
                    viewModel.addMaterial(
                        Material(
                            code = System.currentTimeMillis().toString(),
                            name = materialName,
                            description = "",
                            brand = "",
                            quantity = qty,
                            unitPrice = price,
                            totalPrice = qty * price
                        )
                    )
                    materialName = ""
                    quantity = ""
                    unitPrice = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Agregar", fontSize = 14.sp)
        }
    }
}