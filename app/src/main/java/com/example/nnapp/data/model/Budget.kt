package com.example.nnapp.data.model

data class Budget(
    val id: String = "",
    val clientName: String = "",
    val address: String = "",
    val dni: String = "",
    val phone: String = "",
    val materials: List<Material> = emptyList(),
    val confirmed: Boolean = false,
    val creationDate: String = ""
)