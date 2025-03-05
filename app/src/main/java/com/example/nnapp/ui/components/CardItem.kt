package com.example.nnapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CardItem(label: String, size: Dp) {
    Card(
        modifier = Modifier
            .size(size) // Tamaño cuadrado dinámico
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(label, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview
@Composable
fun CardItemPreview() {
    CardItem("Preview", 100.dp)
}