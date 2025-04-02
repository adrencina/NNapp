package com.example.nnapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(step: Int, totalSteps: Int = 3) {
    val progress = step / totalSteps.toFloat()
    // Indicador lineal ubicado siempre en la misma posición (arriba)
    LinearProgressIndicator(
    progress = { progress },
    modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
    color = Color.Gray,
    trackColor = Color.LightGray,
    )
}