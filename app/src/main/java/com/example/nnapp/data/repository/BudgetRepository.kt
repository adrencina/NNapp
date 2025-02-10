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
        val docRef = budgetCollection.add(budget).await()
        return docRef.id
    }

    // Obtiene todos los presupuestos
    suspend fun getBudgets(): List<Budget> {
        return budgetCollection.get().await().documents.mapNotNull { doc ->
            doc.toObject(Budget::class.java)?.copy(id = doc.id)
        }
    }

    // Obtiene un presupuesto por ID (se expone para edición)
    suspend fun getBudgetById(id: String): Budget? {
        val doc = budgetCollection.document(id).get().await()
        return if (doc.exists()) doc.toObject(Budget::class.java)?.copy(id = doc.id) else null
    }

    // Actualiza un presupuesto completo
    suspend fun updateBudget(budget: Budget) {
        budgetCollection.document(budget.id).update(
            "name", budget.clientName,
            "materials", budget.materials
        ).await()
    }

    // Agrega un material al presupuesto (usando un array en Firestore)
    suspend fun addMaterial(budgetId: String, material: Material) {
        val docRef = budgetCollection.document(budgetId)
        docRef.update("materials", FieldValue.arrayUnion(material)).await()
    }

    // Actualiza un material específico dentro del presupuesto
    suspend fun updateMaterial(budgetId: String, material: Material) {
        val docRef = budgetCollection.document(budgetId)
        val budget = getBudgetById(budgetId) ?: return
        val updatedMaterials = budget.materials.map {
            if (it.code == material.code) material else it
        }
        docRef.update("materials", updatedMaterials).await()
    }

    // Elimina un material del presupuesto
    suspend fun deleteMaterial(budgetId: String, material: Material) {
        val docRef = budgetCollection.document(budgetId)
        val budget = getBudgetById(budgetId) ?: return
        val updatedMaterials = budget.materials.filterNot { it.code == material.code }
        docRef.update("materials", updatedMaterials).await()
    }

    // Elimina un presupuesto completo
    suspend fun deleteBudget(id: String) {
        budgetCollection.document(id).delete().await()
    }
}