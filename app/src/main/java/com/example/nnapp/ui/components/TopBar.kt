package com.example.nnapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(text: String, navController: NavController, onThemeToggle: () -> Unit) {
    val isDarkTheme = remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text) },
        actions = {
            // Botón para cambiar el tema
            IconButton(onClick = {
                isDarkTheme.value = !isDarkTheme.value
                onThemeToggle()
            }) {
                Icon(
                    imageVector = if (isDarkTheme.value) Icons.Filled.Brightness7 else Icons.Filled.Brightness4,
                    contentDescription = "Cambiar tema"
                )
            }

            // Botón de logout
            IconButton(onClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate("auth") {
                    popUpTo("home") { inclusive = true }
                }
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = "Cerrar sesión")
            }
        }
    )
}