package com.example.nnapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nnapp.ui.viewmodel.ThemeViewModel

@Composable
fun ThemeToggleButton(viewModel: ThemeViewModel) {
    val isDarkMode = viewModel.darkMode.collectAsState(initial = false)
    IconButton(
        onClick = {
            viewModel.toggleTheme()
        },
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = if (isDarkMode.value) Icons.Default.LightMode else Icons.Default.DarkMode,
            contentDescription = "Toggle Theme"
        )
    }
}