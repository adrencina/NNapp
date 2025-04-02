package com.example.nnapp.data.model

/**
 * Modelo de datos para un Proyecto.
 * Se basa en la estructura de un presupuesto, agregando campos específicos para proyectos.
 */
data class Project(
    val id: String = "",
    val projectName: String = "",       // Nombre del proyecto
    val clientName: String = "",        // Nombre del cliente
    val address: String = "",           // Dirección del cliente/proyecto
    val dni: String = "",               // DNI del cliente
    val phone: String = "",             // Teléfono del cliente
    val startDate: String = "",         // Fecha de inicio del proyecto
    val endDate: String = "",           // Fecha estimada de finalización
    val description: String = "",       // Descripción o comentarios del proyecto
    val materials: List<Material> = emptyList(), // Lista de materiales usados
    val creationDate: String = ""       // Fecha de creación del proyecto
)