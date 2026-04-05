package org.example.products.infra.persistence

import org.example.catalog.infra.persistence.CategoriesTable
import org.example.catalog.infra.persistence.RegionsTable
import org.example.users.infra.persistence.UsersTable
import org.jetbrains.exposed.sql.Table

object ProductsTable : Table("productos") {
    val id = integer("id").autoIncrement()
    val nombre = varchar("nombre", 150)
    val precio = decimal("precio", 10, 2)
    val descripcion = varchar("descripcion", 200)
    val imagenUrl = text("imagen_url")
    val categoriaId = integer("categoria_id").references(CategoriesTable.id)
    val regionId = integer("region_id").references(RegionsTable.id)
    val usuarioId = integer("usuario_id").references(UsersTable.id)

    override val primaryKey = PrimaryKey(id)
}