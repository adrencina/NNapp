package com.example.nnapp.ui.home.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.nnapp.data.model.Budget
import com.example.nnapp.ui.components.BudgetCard

/**
 * Muestra una fila horizontal de tarjetas de presupuesto.
 * Cada tarjeta se muestra en un tamaño dinámico (cardSize) y se separa con un espaciado de 16.dp.
 */
@Composable
fun BudgetLazyRowSection(
    budgets: List<Budget>,
    cardSize: Dp,
    onEdit: (Budget) -> Unit,
    onView: (Budget) -> Unit,
    onDelete: (Budget) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(budgets) { budget ->
            BudgetCard(
                budget = budget,
                size = cardSize,
                onEdit = onEdit,
                onView = onView,
                onDelete = onDelete
            )
        }
    }
}