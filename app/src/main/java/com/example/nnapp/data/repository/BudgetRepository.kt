package com.example.nnapp.data.repository

import com.example.nnapp.data.model.Budget
import com.example.nnapp.data.model.Material
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BudgetRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val budgetCollection = firestore.collection("budgets")

    suspend fun createBudget(budget: Budget): String {
        val docRef = budgetCollection.add(budget).await()
        return docRef.id
    }

    suspend fun getBudgets(): List<Budget> {
        return budgetCollection.get().await().documents.mapNotNull { doc ->
            doc.toObject(Budget::class.java)?.copy(id = doc.id)
        }
    }

    private suspend fun getBudgetById(id: String): Budget? {
        val doc = budgetCollection.document(id).get().await()
        return doc.toObject(Budget::class.java)?.copy(id = doc.id)
    }

    suspend fun addMaterial(budgetId: String, material: Material) {
        val docRef = budgetCollection.document(budgetId)
        docRef.update("materials", FieldValue.arrayUnion(material)).await()
    }

    suspend fun updateMaterial(budgetId: String, material: Material) {
        val docRef = budgetCollection.document(budgetId)
        val budget = getBudgetById(budgetId) ?: return
        val updatedMaterials = budget.materials.map {
            if (it.code == material.code) material else it
        }
        docRef.update("materials", updatedMaterials).await()
    }

    suspend fun deleteMaterial(budgetId: String, material: Material) {
        val docRef = budgetCollection.document(budgetId)
        val budget = getBudgetById(budgetId) ?: return
        val updatedMaterials = budget.materials.filterNot { it.code == material.code }
        docRef.update("materials", updatedMaterials).await()
    }

    suspend fun deleteBudget(id: String) {
        budgetCollection.document(id).delete().await()
    }
}