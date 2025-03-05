package com.example.nnapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.nnapp.data.model.Budget

@Composable
fun BudgetCard(
    budget: Budget,
    size: Dp,
    onEdit: (Budget) -> Unit,
    onView: (Budget) -> Unit,
    onDelete: (Budget) -> Unit
) {
    Card(
        modifier = Modifier
            .size(size)
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        // Contenido de la tarjeta de presupuesto
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Mostrar el nombre del cliente (u otra info relevante)
            Text(
                text = budget.clientName,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )
            // Fila con los íconos de editar, ver y eliminar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar presupuesto",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onEdit(budget) }
                )
                Icon(
                    imageVector = Icons.Default.Visibility,
                    contentDescription = "Ver presupuesto",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onView(budget) }
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar presupuesto",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onDelete(budget) }
                )
            }
        }
    }
}