package org.example.products.domain.repository

import org.example.products.app.dto.ProductResponse
import org.example.products.domain.models.Product

interface ProductRepository {
    fun findAll(searchQuery: String?, categoryId: Int?, regionId: Int?): List<ProductResponse>
    fun findByUserId(userId: Int): List<ProductResponse> // NUEVO
    fun findById(id: Int): ProductResponse?
    fun save(product: Product): Product
    fun update(id: Int, userId: Int, nombre: String, precio: Double, descripcion: String, categoriaId: Int, regionId: Int, nuevaImagenUrl: String?): Boolean
    fun delete(productId: Int, userId: Int): Boolean
}