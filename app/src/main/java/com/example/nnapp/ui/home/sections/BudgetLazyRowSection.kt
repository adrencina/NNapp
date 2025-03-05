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

@Composable
fun BudgetLazyRowSection(
    budgets: List<Budget>,
    cardSize: Dp,
    onEdit: (Budget) -> Unit,
    onView: (Budget) -> Unit,
    onDelete: (Budget) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
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