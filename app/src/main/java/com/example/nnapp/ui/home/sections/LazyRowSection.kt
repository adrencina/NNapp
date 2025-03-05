package com.example.nnapp.ui.home.sections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nnapp.ui.components.CardItem

@Composable
fun LazyRowSection(data: List<String>) {
    BoxWithConstraints {
        val screenWidth = maxWidth
        val screenHeight = maxHeight
        val cardSize = screenHeight * 0.25f // Las tarjetas serán cuadradas y ocuparán el 25% de la altura de la pantalla

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(cardSize), // Define la altura del LazyRow
            horizontalArrangement = Arrangement.spacedBy(16.dp), // Espaciado entre tarjetas
            contentPadding = PaddingValues(horizontal = 16.dp) // Padding externo en los lados
        ) {
            items(data) { item ->
                CardItem(
                    label = item,
                    size = cardSize
                )
            }
        }
    }
}