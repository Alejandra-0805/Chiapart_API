package org.example.products.app

import org.example.products.app.dto.ProductResponse
import org.example.products.domain.repository.ProductRepository

class GetProductById(private val repository: ProductRepository) {
    fun execute(id: Int): ProductResponse? {
        return repository.findById(id)
    }
}