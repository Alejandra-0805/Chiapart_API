package org.example.products.app

import org.example.products.domain.repository.ProductRepository

class DeleteProduct(private val repository: ProductRepository) {
    fun execute(productId: Int, userId: Int): Boolean {
        return repository.delete(productId, userId)
    }
}