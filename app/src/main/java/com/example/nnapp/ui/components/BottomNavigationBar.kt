package com.example.nnapp.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.nnapp.ui.navigation.NavigationRoute
import com.example.nnapp.utils.theme.BackgroundColor
import com.example.nnapp.utils.theme.ElectricGrayLight
import com.example.nnapp.utils.theme.PrimaryColor

/**
 * Barra de navegación inferior flotante con diseño en formato de "panel".
 *
 * Características:
 * - Se muestra flotante con un margen horizontal de 16.dp y 16.dp inferior.
 * - Altura definida como un 8% de la pantalla.
 * - Fondo semi-transparente (BackGroundColor con alpha 0.5) y borde sólido de 2.dp en gris claro.
 * - Bordes redondeados (forma "pastilla").
 * - Los íconos tienen un tamaño fijo de 32.dp.
 * - Al ser seleccionado, el ícono cambia de variante (outlined a filled) y su tint cambia de color.
 * - La distribución horizontal es fija: cada ícono se coloca en un contenedor con peso 1,
 *   de forma que el cambio de variante no afecta la separación entre ellos.
 * - Se desactivan los efectos visuales por defecto al pulsar (sin ripple ni sombreado).
 */
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun BottomNavigationBar(navController: NavController) {
    // Obtenemos la altura de la pantalla para definir la altura de la barra.
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    // Altura de la barra: 8% de la pantalla.
    val navigationBarHeight = screenHeight * 0.08f
    // Márgenes para el efecto "flotante".
    val horizontalPadding = 16.dp
    val bottomPadding = 16.dp

    // Lista de elementos de navegación con sus variantes outlined y filled.
    val items = listOf(
        NavigationItem(NavigationRoute.Home, Icons.Outlined.Home, Icons.Filled.Home),
        NavigationItem(NavigationRoute.Academy, Icons.AutoMirrored.Outlined.MenuBook,
            Icons.AutoMirrored.Filled.MenuBook),
        NavigationItem(NavigationRoute.Camera, Icons.Outlined.Camera, Icons.Filled.Camera),
        NavigationItem(NavigationRoute.Chat, Icons.AutoMirrored.Outlined.Chat, Icons.AutoMirrored.Filled.Chat),
        NavigationItem(NavigationRoute.Settings, Icons.Outlined.Settings, Icons.Filled.Settings)
    )

    // Obtenemos la ruta actual para determinar cuál ícono está seleccionado.
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            // Se aplican márgenes horizontal y vertical para que la Card "flote" sobre el Scaffold.
            .padding(horizontal = horizontalPadding, vertical = bottomPadding)
    ) {
        Card(
            shape = RoundedCornerShape(50), // Bordes redondeados tipo "pastilla".
            colors = CardDefaults.cardColors(
                containerColor = BackgroundColor.copy(alpha = 0.5f) // Fondo semi-transparente.
            ),
            border = BorderStroke(2.dp, ElectricGrayLight), // Borde sólido de 2.dp en gris claro.
            modifier = Modifier
                .fillMaxWidth()
                .height(navigationBarHeight)
        ) {
            // Usamos un Row con contenedores de peso fijo para que la distribución de los íconos sea constante.
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tamaño fijo para los íconos (según estándares de UI).
                val iconSize = 32.dp

                items.forEach { item ->
                    // Determinamos si el ícono está seleccionado.
                    val isSelected = currentRoute == item.route.route

                    // Cada ícono se coloca en un contenedor con peso fijo para mantener la posición.
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        // IconButton sin efectos de ripple (usamos una InteractionSource sin indicación)
                        IconButton(
                            onClick = {
                                if (!isSelected) {
                                    navController.navigate(item.route.route) {
                                        popUpTo(NavigationRoute.Home.route) { inclusive = false }
                                        launchSingleTop = true
                                    }
                                }
                            },
                            modifier = Modifier.size(iconSize),
                            interactionSource = MutableInteractionSource() // Sin ripple por defecto
                        ) {
                            Icon(
                                imageVector = if (isSelected) item.filledIcon else item.outlinedIcon,
                                contentDescription = item.route.route,
                                tint = if (isSelected) PrimaryColor else ElectricGrayLight
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Clase de datos para manejar los íconos de la barra de navegación.
 */
data class NavigationItem(
    val route: NavigationRoute,
    val outlinedIcon: ImageVector,
    val filledIcon: ImageVector
)