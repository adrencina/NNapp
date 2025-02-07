package com.example.nnapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nnapp.data.model.Budget
import com.example.nnapp.data.model.Material
import com.example.nnapp.data.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val repository: BudgetRepository
) : ViewModel() {

    private val _budgets = MutableStateFlow<List<Budget>>(emptyList())
    val budgets: StateFlow<List<Budget>> = _budgets

    init {
        loadBudgets()
    }

    private fun loadBudgets() {
        viewModelScope.launch {
            _budgets.value = repository.getBudgets()
        }
    }

    fun addBudget(budget: Budget) {
        viewModelScope.launch {
            repository.createBudget(budget)
            loadBudgets()
        }
    }

    fun addMaterial(budgetId: String, material: Material) {
        viewModelScope.launch {
            repository.addMaterial(budgetId, material)
            loadBudgets()
        }
    }

    fun updateMaterial(budgetId: String, material: Material) {
        viewModelScope.launch {
            repository.updateMaterial(budgetId, material)
            loadBudgets()
        }
    }

    fun deleteMaterial(budgetId: String, material: Material) {
        viewModelScope.launch {
            repository.deleteMaterial(budgetId, material)
            loadBudgets()
        }
    }

    fun deleteBudget(id: String) {
        viewModelScope.launch {
            repository.deleteBudget(id)
            loadBudgets()
        }
    }
}