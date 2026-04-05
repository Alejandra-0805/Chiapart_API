package org.example.catalog.app.dto

import kotlinx.serialization.Serializable

@Serializable data class CategoryResponse(val id: Int, val nombre: String)
@Serializable data class RegionResponse(val id: Int, val nombre: String)