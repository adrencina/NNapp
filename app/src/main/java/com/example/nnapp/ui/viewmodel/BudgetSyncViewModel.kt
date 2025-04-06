package com.example.nnapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nnapp.data.entity.BudgetEntity
import com.example.nnapp.data.repository.BudgetLocalRepository
import com.example.nnapp.data.repository.BudgetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para sincronizar presupuestos:
 * - Guarda y actualiza presupuestos localmente (Room) y en Firebase.
 */
class BudgetSyncViewModel @Inject constructor(
    private val localRepository: BudgetLocalRepository,
    private val cloudRepository: com.example.nnapp.data.repository.BudgetRepository
) : ViewModel() {

    private val _budgets = MutableStateFlow<List<BudgetEntity>>(emptyList())
    val budgets: StateFlow<List<BudgetEntity>> = _budgets

    init {
        viewModelScope.launch {
            localRepository.getAllBudgets().collect { list ->
                _budgets.value = list
            }
        }
    }

//    fun saveBudget(budget: BudgetEntity) {
//        viewModelScope.launch {
//            localRepository.insertBudget(budget)
//            // Convertir a tu modelo de dominio si es necesario y guardarlo en Firebase
//            cloudRepository.createBudget(budget.toBudget())
//        }
//    }

    fun updateBudget(budget: BudgetEntity) {
        viewModelScope.launch {
            localRepository.updateBudget(budget)
            // Actualizar en Firebase
        }
    }

    fun deleteBudget(budget: BudgetEntity) {
        viewModelScope.launch {
            localRepository.deleteBudget(budget)
            // Eliminar en Firebase
        }
    }

////     Método de extensión para convertir BudgetEntity a Budget (asume que tienes una clase Budget en tu dominio)
//    private fun BudgetEntity.toBudget(): com.example.nnapp.data.model.Budget {
//        return com.example.nnapp.data.model.Budget(
//            id = this.id.toString(),
//            client = this.clientName,
//            address = this.clientAddress,
//            dni = "", // Agregar si aplica
//            phone = this.clientPhone,
//            materials = this.materials,
//            confirmed = false,
//            creationDate = this.creationDate
//        )
//    }
}