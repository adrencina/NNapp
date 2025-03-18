package com.example.nnapp.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nnapp.utils.theme.ElectricGrayLight
import com.google.firebase.auth.FirebaseAuth

/**
 * Barra superior de la aplicación.
 *
 * Se mantiene la forma estándar del TopAppBar, pero se ajusta el tamaño de los íconos a 32.dp
 * y se utiliza el color ElectricGrayLight para que coincida con la estética de la BottomNavigationBar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(text: String, navController: NavController, onThemeToggle: () -> Unit) {
    // Estado local para el cambio de tema (demo; en producción se maneja en un ViewModel)
    val isDarkTheme = remember { mutableStateOf(false) }
    // Se define un tamaño fijo de 32.dp para los íconos, en línea con la BottomNavigationBar.
    val iconSize = 25.dp

    TopAppBar(
        title = { Text(text) },
        // Fondo transparente para que se integre con el contenido de la pantalla
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        actions = {
            // Botón para cambiar el tema
            IconButton(
                onClick = {
                    isDarkTheme.value = !isDarkTheme.value
                    onThemeToggle()
                }
            ) {
                Icon(
                    imageVector = if (isDarkTheme.value) Icons.Filled.Brightness7 else Icons.Filled.Brightness4,
                    contentDescription = "Cambiar tema",
                    modifier = Modifier.size(iconSize),
                    tint = ElectricGrayLight
                )
            }
            // Botón para cerrar sesión
            IconButton(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate("auth") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Cerrar sesión",
                    modifier = Modifier.size(iconSize),
                    tint = ElectricGrayLight
                )
            }
        }
    )
}