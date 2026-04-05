package org.example.catalog.infra

import io.ktor.server.application.*
import org.example.catalog.app.GetCategories
import org.example.catalog.app.GetRegions
import org.example.catalog.infra.persistence.PostgresCatalogRepository
import org.example.catalog.infra.routing.catalogRoutes

fun Application.initCatalogModule() {
    val repository = PostgresCatalogRepository()
    catalogRoutes(
        getCategories = GetCategories(repository),
        getRegions = GetRegions(repository)
    )
}