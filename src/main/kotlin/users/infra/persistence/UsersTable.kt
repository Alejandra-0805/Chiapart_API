package org.example.users.infra.persistence

import org.jetbrains.exposed.sql.Table

object UsersTable : Table("usuarios") {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 255).uniqueIndex()
    val contrasena = varchar("contrasena", 255)
    val username = varchar("username", 100)

    override val primaryKey = PrimaryKey(id)
}