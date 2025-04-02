package com.example.nnapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BudgetTypeSelector(
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    val budgetTypes = listOf("Obra Nueva" to Icons.Default.Home, "Reparación" to Icons.Default.Build)

    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        budgetTypes.forEach { (label, icon) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onTypeSelected(label) }
                    .padding(8.dp)
            ) {
                Icon(imageVector = icon, contentDescription = label)
                Text(text = label)
            }
        }
    }
}