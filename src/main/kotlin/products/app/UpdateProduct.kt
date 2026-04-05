package org.example.products.app

import org.example.products.domain.repository.ImageStorage
import org.example.products.domain.repository.ProductRepository

class UpdateProduct(
    private val repository: ProductRepository,
    private val imageStorage: ImageStorage
) {
    fun execute(
        productId: Int, userId: Int, nombre: String, precio: Double,
        descripcion: String, categoriaId: Int, regionId: Int, imageBytes: ByteArray?
    ): Boolean {
        val nuevaImagenUrl = if (imageBytes != null && imageBytes.isNotEmpty()) {
            imageStorage.uploadImage(imageBytes)
        } else {
            null
        }

        return repository.update(productId, userId, nombre, precio, descripcion, categoriaId, regionId, nuevaImagenUrl)
    }
}