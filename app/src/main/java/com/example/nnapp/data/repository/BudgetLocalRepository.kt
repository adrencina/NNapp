package com.example.nnapp.data.repository

import com.example.nnapp.data.dao.BudgetDao
import com.example.nnapp.data.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositorio local que maneja las operaciones CRUD sobre Room.
 */
@Singleton
class BudgetLocalRepository @Inject constructor(
    private val budgetDao: BudgetDao
) {
    fun getAllBudgets(): Flow<List<BudgetEntity>> = budgetDao.getAllBudgets()

    suspend fun getBudgetById(id: Int): BudgetEntity? = budgetDao.getBudgetById(id)

    suspend fun insertBudget(budget: BudgetEntity) = budgetDao.insertBudget(budget)

    suspend fun updateBudget(budget: BudgetEntity) = budgetDao.updateBudget(budget)

    suspend fun deleteBudget(budget: BudgetEntity) = budgetDao.deleteBudget(budget)
}