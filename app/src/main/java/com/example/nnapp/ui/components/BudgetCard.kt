package com.example.nnapp.ui.components

import androidx.compose.foundation.BorderStroke
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
import com.example.nnapp.utils.theme.BackgroundColor
import com.example.nnapp.utils.theme.ElectricGrayLight

/**
 * Tarjeta que muestra información resumida de un presupuesto.
 *
 * Se adapta al estilo de la navbar inferior flotante:
 * - Fondo solido por la elevation de la tarjeta.
 * - Borde sólido de 2.dp en gris claro (ElectricGrayLight).
 * - Bordes redondeados (forma "pastilla" con RoundedCornerShape de 16.dp).
 * - Los textos y los íconos se muestran en gris claro para coherencia con la estética.
 */
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
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 2.dp, color = ElectricGrayLight),
        colors = CardDefaults.cardColors(
            // Usamos un fondo solido por la elevation de la tarjeta
            containerColor = BackgroundColor
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Texto con el nombre del cliente en gris claro
            Text(
                text = budget.client.name,
                style = MaterialTheme.typography.titleMedium,
                color = ElectricGrayLight,
                maxLines = 2
            )
            // Fila con botones para editar, ver y eliminar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar presupuesto",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onEdit(budget) },
                    tint = ElectricGrayLight
                )
                Icon(
                    imageVector = Icons.Default.Visibility,
                    contentDescription = "Ver presupuesto",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onView(budget) },
                    tint = ElectricGrayLight
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar presupuesto",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onDelete(budget) },
                    tint = ElectricGrayLight
                )
            }
        }
    }
}