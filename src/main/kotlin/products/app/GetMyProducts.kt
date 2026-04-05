package org.example.products.app

import org.example.products.app.dto.ProductResponse
import org.example.products.domain.repository.ProductRepository

class GetMyProducts(private val repository: ProductRepository) {
    fun execute(userId: Int): List<ProductResponse> {
        return repository.findByUserId(userId)
    }
}