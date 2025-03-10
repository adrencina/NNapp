package com.example.nnapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nnapp.ui.navigation.AppNavigation
import com.example.nnapp.ui.viewmodel.ThemeViewModel
import com.example.nnapp.utils.theme.NNappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Obtener el ThemeViewModel
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val isDarkTheme by themeViewModel.darkMode.collectAsState()

            // Envolver toda la app en NNappTheme, pasando el tema seleccionado
            NNappTheme(darkTheme = isDarkTheme) {
                AppNavigation()
            }
        }
    }
}