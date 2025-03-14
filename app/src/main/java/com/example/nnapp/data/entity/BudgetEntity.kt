package com.example.nnapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.nnapp.data.converter.MaterialConverter
import com.example.nnapp.data.converter.ListStringConverter
import com.example.nnapp.data.model.Material

/**
 * Entidad para almacenar un presupuesto en la base de datos local.
 * Se almacenan los materiales y las formas de pago (List<String>) utilizando TypeConverters.
 */
@Entity(tableName = "budgets")
@TypeConverters(MaterialConverter::class, ListStringConverter::class)
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val clientName: String,
    val clientAddress: String,
    val clientPhone: String,
    val clientEmail: String,
    // Lista de materiales convertida a JSON mediante MaterialConverter
    val materials: List<Material>,
    val expiryDate: String,
    // Lista de formas de pago convertida a JSON mediante ListStringConverter
    val paymentMethods: List<String>,
    val comment: String,
    val pdfUri: String? = null,  // Guardamos el URI del PDF como String
    val creationDate: String
)
