package com.example.nnapp.data.model

data class Material(
    val code: String = "",
    val name: String = "",
    val description: String = "",
    val brand: String = "",
    val quantity: Int = 0,
    val unitPrice: Double = 0.0,
    val totalPrice: Double = 0.0
)