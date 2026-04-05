package org.example.products.infra.persistence

import org.example.catalog.infra.persistence.CategoriesTable
import org.example.catalog.infra.persistence.RegionsTable
import org.example.products.app.dto.ProductResponse
import org.example.products.domain.models.Product
import org.example.products.domain.repository.ProductRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal

class PostgresProductRepository : ProductRepository {

    override fun findAll(searchQuery: String?, categoryId: Int?, regionId: Int?): List<ProductResponse> = transaction {
        val query = ProductsTable
            .innerJoin(CategoriesTable, onColumn = { ProductsTable.categoriaId }, otherColumn = { CategoriesTable.id })
            .innerJoin(RegionsTable, onColumn = { ProductsTable.regionId }, otherColumn = { RegionsTable.id })
            .selectAll()

        if (!searchQuery.isNullOrBlank()) {
            query.andWhere { ProductsTable.nombre.lowerCase() like "%${searchQuery.lowercase()}%" }
        }
        if (categoryId != null) {
            query.andWhere { ProductsTable.categoriaId eq categoryId }
        }
        if (regionId != null) {
            query.andWhere { ProductsTable.regionId eq regionId }
        }

        query.map { mapRowToResponse(it) }
    }

    override fun findByUserId(userId: Int): List<ProductResponse> = transaction {
        ProductsTable
            .innerJoin(CategoriesTable, onColumn = { ProductsTable.categoriaId }, otherColumn = { CategoriesTable.id })
            .innerJoin(RegionsTable, onColumn = { ProductsTable.regionId }, otherColumn = { RegionsTable.id })
            .selectAll()
            .where { ProductsTable.usuarioId eq userId }
            .map { mapRowToResponse(it) }
    }

    override fun findById(id: Int): ProductResponse? = transaction {
        ProductsTable
            .innerJoin(CategoriesTable, onColumn = { ProductsTable.categoriaId }, otherColumn = { CategoriesTable.id })
            .innerJoin(RegionsTable, onColumn = { ProductsTable.regionId }, otherColumn = { RegionsTable.id })
            .selectAll()
            .where { ProductsTable.id eq id }
            .map { mapRowToResponse(it) }
            .singleOrNull()
    }

    override fun save(product: Product): Product = transaction {
        val insertStmt = ProductsTable.insert {
            it[nombre] = product.nombre
            it[precio] = BigDecimal.valueOf(product.precio)
            it[descripcion] = product.descripcion
            it[imagenUrl] = product.imagenUrl
            it[categoriaId] = product.categoriaId
            it[regionId] = product.regionId
            it[usuarioId] = product.usuarioId
        }
        product.copy(id = insertStmt[ProductsTable.id])
    }

    override fun update(id: Int, userId: Int, nombre: String, precio: Double, descripcion: String, categoriaId: Int, regionId: Int, nuevaImagenUrl: String?): Boolean = transaction {
        ProductsTable.update({ (ProductsTable.id eq id) and (ProductsTable.usuarioId eq userId) }) {
            it[this.nombre] = nombre
            it[this.precio] = java.math.BigDecimal.valueOf(precio)
            it[this.descripcion] = descripcion
            it[this.categoriaId] = categoriaId
            it[this.regionId] = regionId
            if (nuevaImagenUrl != null) {
                it[this.imagenUrl] = nuevaImagenUrl
            }
        } > 0
    }

    override fun delete(productId: Int, userId: Int): Boolean = transaction {
        ProductsTable.deleteWhere { (id eq productId) and (usuarioId eq userId) } > 0
    }

    private fun mapRowToResponse(row: ResultRow) = ProductResponse(
        id = row[ProductsTable.id],
        nombre = row[ProductsTable.nombre],
        precio = row[ProductsTable.precio].toDouble(),
        descripcion = row[ProductsTable.descripcion],
        imagenUrl = row[ProductsTable.imagenUrl],
        categoriaId = row[ProductsTable.categoriaId],
        categoriaNombre = row[CategoriesTable.nombre],
        regionId = row[ProductsTable.regionId],
        regionNombre = row[RegionsTable.nombre],
        usuarioId = row[ProductsTable.usuarioId]
    )
}