package com.example.nnapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nnapp.data.datastore.ThemePreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themePreferenceManager: ThemePreferenceManager
) : ViewModel() {

    // Estado del tema (por defecto claro si no hay valor)
    val darkMode = themePreferenceManager.darkModeFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    // Alterna el tema y guarda la preferencia
    fun toggleTheme() {
        viewModelScope.launch {
            themePreferenceManager.setDarkMode(!darkMode.value)
        }
    }
}