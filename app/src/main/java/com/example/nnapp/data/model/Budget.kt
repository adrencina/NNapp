package com.example.nnapp.data.model

data class Budget(
    val id: String = "",
    val client: Client = Client(),
    val materials: List<Material> = emptyList(),
    val confirmed: Boolean = false,
    val creationDate: String = ""
)