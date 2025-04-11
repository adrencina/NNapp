package com.example.nnapp.ui.budget.screens.materials

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nnapp.data.model.Material

@Composable
fun MaterialItem(material: Material) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray)
            .padding(8.dp),
        color = Color.White
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = material.name,
                modifier = Modifier.weight(2f),
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                text = "${material.quantity}",
                modifier = Modifier.weight(1f),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Text(
                text = "$${"%.2f".format(material.unitPrice)}",
                modifier = Modifier.weight(2f),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Text(
                text = "$${"%.2f".format(material.totalPrice)}",
                modifier = Modifier.weight(2f),
                fontSize = 14.sp,
                textAlign = TextAlign.End,
                color = Color.Black
            )
        }
    }
}