package org.example.users.infra.persistence

import org.example.users.domain.models.User
import org.example.users.domain.repository.UserRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PostgresUserRepository : UserRepository {
    override fun findByEmail(email: String): User? {
        return transaction {
            UsersTable.selectAll()
                .where { UsersTable.email eq email }
                .map {
                    User(
                        id = it[UsersTable.id],
                        email = it[UsersTable.email],
                        passwordHash = it[UsersTable.contrasena],
                        username = it[UsersTable.username]
                    )
                }
                .singleOrNull()
        }
    }

    override fun save(user: User): User {
        return transaction {
            val insertStmt = UsersTable.insert {
                it[email] = user.email
                it[contrasena] = user.passwordHash
                it[username] = user.username
            }
            user.copy(id = insertStmt[UsersTable.id])
        }
    }
}