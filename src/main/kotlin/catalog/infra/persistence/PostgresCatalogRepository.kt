package org.example.catalog.infra.persistence

import org.example.catalog.domain.models.Category
import org.example.catalog.domain.models.Region
import org.example.catalog.domain.repository.CatalogRepository
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class PostgresCatalogRepository : CatalogRepository {
    override fun getAllCategories(): List<Category> = transaction {
        CategoriesTable.selectAll().map {
            Category(id = it[CategoriesTable.id], nombre = it[CategoriesTable.nombre])
        }
    }

    override fun getAllRegions(): List<Region> = transaction {
        RegionsTable.selectAll().map {
            Region(id = it[RegionsTable.id], nombre = it[RegionsTable.nombre])
        }
    }
}