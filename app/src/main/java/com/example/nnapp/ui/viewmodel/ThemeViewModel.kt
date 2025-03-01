package com.example.nnapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nnapp.data.datastore.ThemePreferences
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ThemeViewModel(private val themePreferences: ThemePreferences) : ViewModel() {
    private val _darkMode = MutableStateFlow(false)
    val darkMode: StateFlow<Boolean> get() = _darkMode

    init {
        viewModelScope.launch {
            themePreferences.darkModeFlow.collect { isDark ->
                _darkMode.value = isDark
            }
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            themePreferences.saveDarkMode(!_darkMode.value)
        }
    }
}