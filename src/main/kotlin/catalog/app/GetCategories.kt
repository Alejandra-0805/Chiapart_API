package org.example.catalog.app

import org.example.catalog.app.dto.CategoryResponse
import org.example.catalog.domain.repository.CatalogRepository

class GetCategories(private val repository: CatalogRepository) {
    fun execute(): List<CategoryResponse> {
        return repository.getAllCategories().map { CategoryResponse(it.id, it.nombre) }
    }
}