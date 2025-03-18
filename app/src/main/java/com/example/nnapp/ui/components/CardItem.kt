package com.example.nnapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.nnapp.utils.theme.BackgroundColor
import com.example.nnapp.utils.theme.ElectricGrayLight

/**
 * Componente de tarjeta cuadrada que muestra un texto centrado.
 * Se adapta al mismo estilo que la tarjeta de presupuesto:
 * - Bordes redondeados (RoundedCornerShape de 16.dp).
 * - Borde sólido de 2.dp en ElectricGrayLight.
 * - Fondo con BackgroundColor.
 * - Padding interno de 12.dp.
 * - Texto con MaterialTheme.typography.bodyLarge y tint en ElectricGrayLight.
 */
@Composable
fun CardItem(label: String, size: Dp) {
    Card(
        modifier = Modifier
            .size(size)
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, ElectricGrayLight),
        colors = CardDefaults.cardColors(containerColor = BackgroundColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = ElectricGrayLight
            )
        }
    }
}

@Preview
@Composable
fun CardItemPreview() {
    CardItem("Preview", 100.dp)
}