package org.example.products.app.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val descripcion: String,
    val imagenUrl: String,
    val categoriaId: Int,
    val categoriaNombre: String,
    val regionId: Int,
    val regionNombre: String,
    val usuarioId: Int
)