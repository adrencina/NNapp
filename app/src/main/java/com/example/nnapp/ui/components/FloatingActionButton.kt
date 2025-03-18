package com.example.nnapp.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Botón de acción flotante personalizado.
 * Usa el color primario para el fondo y onPrimary para el ícono, adaptándose al tema.
 */
@Composable
fun FloatingActionButton(icon: ImageVector, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Agregar"
        )
    }
}