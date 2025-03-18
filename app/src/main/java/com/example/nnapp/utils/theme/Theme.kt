package com.example.nnapp.utils.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Definición del esquema de colores para el modo claro usando la paleta propuesta
private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    background = BackgroundColor,
    onBackground = TextColor,
    surface = ElectricGrayLight,       // Usamos el gris claro para superficies
    onSurface = TextColor,
    tertiary = DarkTextColor           // Opcional: para acentos secundarios
)

// Esquema de colores para el modo oscuro; se ajusta para mantener buen contraste
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,            // Podemos usar el mismo o una variante más brillante
    secondary = SecondaryColor,
    background = Color(0xFF121212),     // Fondo oscuro estándar
    onBackground = White,              // Texto blanco en fondo oscuro
    surface = Color(0xFF1E1E1E),        // Superficies oscuras
    onSurface = White,
    tertiary = Color(0xFFAAAAAA)        // Terceros tonos para elementos secundarios
)

@Composable
fun NNappTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Selecciona el esquema de colores basado en el tema actual
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography, // Se usa la tipografía definida en Typography.kt
        content = content
    )
}