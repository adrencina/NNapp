package com.example.nnapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClientDataViewModel @Inject constructor() : ViewModel() {
    var currentStep by mutableIntStateOf(1) // Indica la sección actual (1 a 3)
        private set

    var clientName by mutableStateOf("")
    var address by mutableStateOf("")
    var dni by mutableStateOf("")
    var phone by mutableStateOf("")
    var isFinalConsumer by mutableStateOf(false)

    fun nextStep() {
        if (currentStep < 3) currentStep++
    }

    fun prevStep() {
        if (currentStep > 1) currentStep--
    }

    fun isValidStep(): Boolean {
        return when (currentStep) {
            1 -> clientName.isNotBlank() && address.isNotBlank() && dni.length == 8 && phone.length >= 7
            2 -> true // Validaciones para productos/servicios
            else -> true
        }
    }
}