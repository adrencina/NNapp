package com.example.nnapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nnapp.data.model.Budget
import com.example.nnapp.data.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val repository: BudgetRepository
) : ViewModel() {

    private val _budgets = MutableStateFlow<List<Budget>>(emptyList())
    val budgets: StateFlow<List<Budget>> = _budgets

    // Flujo de presupuestos sin confirmar, ordenados por fecha (más recientes primero)
    val unconfirmedBudgets: StateFlow<List<Budget>> = _budgets
        .map { budgets -> budgets.filter { !it.confirmed }.sortedByDescending { it.creationDate } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        loadBudgets()
    }

    private fun loadBudgets() {
        viewModelScope.launch {
            _budgets.value = repository.getBudgets()
        }
    }

    fun saveBudget(budget: Budget) {
        viewModelScope.launch {
            if (budget.id.isEmpty()) repository.createBudget(budget) else repository.updateBudget(budget)
            loadBudgets()
        }
    }

    suspend fun getBudgetById(id: String): Budget? {
        return repository.getBudgetById(id)
    }

    fun deleteBudget(id: String) {
        viewModelScope.launch {
            repository.deleteBudget(id)
            loadBudgets()
        }
    }
}