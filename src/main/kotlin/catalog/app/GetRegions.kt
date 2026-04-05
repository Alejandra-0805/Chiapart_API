package org.example.catalog.app

import org.example.catalog.app.dto.RegionResponse
import org.example.catalog.domain.repository.CatalogRepository

class GetRegions(private val repository: CatalogRepository) {
    fun execute(): List<RegionResponse> {
        return repository.getAllRegions().map { RegionResponse(it.id, it.nombre) }
    }
}