package org.example.products.app

import org.example.products.domain.models.Product
import org.example.products.domain.repository.ImageStorage
import org.example.products.domain.repository.ProductRepository

class CreateProduct(
    private val repository: ProductRepository,
    private val imageStorage: ImageStorage
) {
    fun execute(
        nombre: String, precio: Double, descripcion: String,
        categoriaId: Int, regionId: Int, usuarioId: Int, imageBytes: ByteArray
    ): Product {
        val imageUrl = imageStorage.uploadImage(imageBytes)

        val product = Product(
            nombre = nombre, precio = precio, descripcion = descripcion,
            imagenUrl = imageUrl, categoriaId = categoriaId, regionId = regionId, usuarioId = usuarioId
        )
        return repository.save(product)
    }
}