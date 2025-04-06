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

    // Crea un nuevo presupuesto y retorna el ID generado
    suspend fun createBudget(budget: Budget): String {
        return try {
            val docRef = budgetCollection.add(budget).await()
            docRef.id
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    // Obtiene todos los presupuestos
    suspend fun getBudgets(): List<Budget> {
        return try {
            budgetCollection.get().await().documents.mapNotNull { doc ->
                doc.toObject(Budget::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // Obtiene un presupuesto por ID
    suspend fun getBudgetById(id: String): Budget? {
        return try {
            val doc = budgetCollection.document(id).get().await()
            if (doc.exists()) doc.toObject(Budget::class.java)?.copy(id = doc.id) else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Actualiza un presupuesto completo
    suspend fun updateBudget(budget: Budget) {
        try {
            budgetCollection.document(budget.id).update(
                "clientName", budget.client.name,
                "materials", budget.materials
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Agrega un material al presupuesto
    suspend fun addMaterial(budgetId: String, material: Material) {
        try {
            val docRef = budgetCollection.document(budgetId)
            docRef.update("materials", FieldValue.arrayUnion(material)).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Actualiza un material específico dentro del presupuesto
    suspend fun updateMaterial(budgetId: String, material: Material) {
        try {
            val docRef = budgetCollection.document(budgetId)
            val budget = getBudgetById(budgetId) ?: return
            val updatedMaterials = budget.materials.map {
                if (it.code == material.code) material else it
            }
            docRef.update("materials", updatedMaterials).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Elimina un material del presupuesto
    suspend fun deleteMaterial(budgetId: String, material: Material) {
        try {
            val docRef = budgetCollection.document(budgetId)
            val budget = getBudgetById(budgetId) ?: return
            val updatedMaterials = budget.materials.filterNot { it.code == material.code }
            docRef.update("materials", updatedMaterials).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Elimina un presupuesto completo
    suspend fun deleteBudget(id: String) {
        try {
            budgetCollection.document(id).delete().await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}