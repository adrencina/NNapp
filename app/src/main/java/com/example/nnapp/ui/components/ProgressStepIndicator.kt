package com.example.nnapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProgressStepIndicator(
    currentStep: Int,
    totalSteps: Int,
    stepLabels: List<String>
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(totalSteps) { index ->
            val stepNumber = index + 1
            val isActive = stepNumber == currentStep
            val color = if (isActive) MaterialTheme.colorScheme.primary else Color.Gray

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .border(2.dp, color, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "$stepNumber", fontSize = 14.sp, color = color)
            }

            if (index < totalSteps - 1) {
                Spacer(modifier = Modifier.width(12.dp))
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = stepLabels[currentStep - 1], fontSize = 16.sp)
            if (currentStep < totalSteps) {
                Text(text = "Siguiente: ${stepLabels[currentStep]}", fontSize = 12.sp)
            }
        }
    }
}