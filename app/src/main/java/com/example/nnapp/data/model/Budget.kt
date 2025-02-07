package com.example.nnapp.data.model

data class Budget(
    val id: String = "",
    val name: String = "",
    val creationDate: String = "",
    val materials: List<Material> = emptyList()
)