package org.example.catalog.infra.persistence

import org.jetbrains.exposed.sql.Table

object CategoriesTable : Table("categorias") {
    val id = integer("id").autoIncrement()
    val nombre = varchar("nombre", 100).uniqueIndex()
    override val primaryKey = PrimaryKey(id)
}

object RegionsTable : Table("regiones") {
    val id = integer("id").autoIncrement()
    val nombre = varchar("nombre", 100).uniqueIndex()
    override val primaryKey = PrimaryKey(id)
}