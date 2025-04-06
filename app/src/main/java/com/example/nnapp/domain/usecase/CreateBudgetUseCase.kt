package com.example.nnapp.domain.usecase

import com.example.nnapp.data.model.Budget
import com.example.nnapp.data.model.Client
import com.example.nnapp.data.model.Material
import com.example.nnapp.data.repository.BudgetRepository
import javax.inject.Inject

class CreateBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(
        client: Client,
        materials: List<Material>,
        creationDate: String
    ): String {
        val budget = Budget(
            id = "",
            client = client,
            materials = materials,
            confirmed = true,
            creationDate = creationDate
        )
        return repository.createBudget(budget)
    }
}