package com.example.nnapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nnapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _authState = MutableLiveData<Boolean>()
    val authState: LiveData<Boolean> get() = _authState

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun register(email: String, password: String) {
        authRepository.registerUser(email, password) { success, error ->
            if (success) {
                _authState.value = true
            } else {
                _errorMessage.value = error
            }
        }
    }

    fun login(email: String, password: String) {
        authRepository.loginUser(email, password) { success, error ->
            if (success) {
                _authState.value = true
            } else {
                _errorMessage.value = error
            }
        }
    }

    fun logout() {
        authRepository.logoutUser()
        _authState.value = false
    }

    fun checkUserSession() {
        _authState.value = authRepository.getCurrentUser() != null
    }

    // Método agregado para establecer un mensaje de error
    fun setError(message: String) {
        _errorMessage.value = message
    }
}