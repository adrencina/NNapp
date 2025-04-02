package com.example.nnapp.data.repository

import com.example.nnapp.data.model.Project
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Repositorio para operaciones CRUD de Proyectos en Firebase Firestore.
 */
class ProjectRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    // Colección de proyectos en Firestore
    private val projectCollection = firestore.collection("projects")

    /**
     * Crea un nuevo proyecto y retorna el ID generado.
     */
    suspend fun createProject(project: Project): String {
        return try {
            val docRef = projectCollection.add(project).await()
            docRef.id
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * Obtiene todos los proyectos.
     */
    suspend fun getProjects(): List<Project> {
        return try {
            projectCollection.get().await().documents.mapNotNull { doc ->
                doc.toObject(Project::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /**
     * Obtiene un proyecto por su ID.
     */
    suspend fun getProjectById(id: String): Project? {
        return try {
            val doc = projectCollection.document(id).get().await()
            if (doc.exists()) doc.toObject(Project::class.java)?.copy(id = doc.id) else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Actualiza un proyecto existente.
     */
    suspend fun updateProject(project: Project) {
        try {
            projectCollection.document(project.id).set(project).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Elimina un proyecto.
     */
    suspend fun deleteProject(id: String) {
        try {
            projectCollection.document(id).delete().await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}