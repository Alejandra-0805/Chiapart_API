package org.example.products.domain.models

data class Product(
    val id: Int = 0,
    val nombre: String,
    val precio: Double,
    val descripcion: String,
    val imagenUrl: String,
    val categoriaId: Int,
    val regionId: Int,
    val usuarioId: Int
)