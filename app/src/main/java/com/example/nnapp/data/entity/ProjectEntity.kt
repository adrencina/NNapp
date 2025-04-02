package com.example.nnapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.nnapp.data.converter.MaterialConverter
import com.example.nnapp.data.model.Material

/**
 * Entidad para almacenar un Proyecto en la base de datos local (Room).
 * Se utiliza un convertidor para la lista de materiales.
 */
@Entity(tableName = "projects")
@TypeConverters(MaterialConverter::class)
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val projectName: String,
    val clientName: String,
    val address: String,
    val dni: String,
    val phone: String,
    val startDate: String,
    val endDate: String,
    val description: String,
    val materials: List<Material>,
    val creationDate: String
)