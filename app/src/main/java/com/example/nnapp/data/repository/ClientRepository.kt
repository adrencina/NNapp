package com.example.nnapp.data.repository

import com.example.nnapp.data.model.Client
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ClientRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val clientCollection = firestore.collection("clients")

    suspend fun createClient(client: Client): String {
        return try {
            val docRef = clientCollection.add(client).await()
            docRef.id
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    suspend fun getClients(): List<Client> {
        return try {
            clientCollection.get().await().documents.mapNotNull { doc ->
                doc.toObject(Client::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getClientById(id: String): Client? {
        return try {
            val doc = clientCollection.document(id).get().await()
            if (doc.exists()) doc.toObject(Client::class.java)?.copy(id = doc.id) else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}