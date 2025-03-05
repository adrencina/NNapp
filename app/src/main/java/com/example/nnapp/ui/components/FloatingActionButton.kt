package com.example.nnapp.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun FloatingActionButton(icon: ImageVector, onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(imageVector = icon, contentDescription = "Floating Action Button")
    }
}
