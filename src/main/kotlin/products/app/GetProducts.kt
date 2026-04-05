package org.example.products.app

import org.example.products.app.dto.ProductResponse
import org.example.products.domain.repository.ProductRepository

class GetProducts(private val repository: ProductRepository) {
    fun execute(searchQuery: String?, categoryId: Int?, regionId: Int?): List<ProductResponse> {
        return repository.findAll(searchQuery, categoryId, regionId)
    }
}