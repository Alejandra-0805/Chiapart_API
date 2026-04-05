package org.example.catalog.domain.repository

import org.example.catalog.domain.models.Category
import org.example.catalog.domain.models.Region

interface CatalogRepository {
    fun getAllCategories(): List<Category>
    fun getAllRegions(): List<Region>
}