package org.example.core.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.cdimascio.dotenv.dotenv
import org.jetbrains.exposed.sql.Database

object DatabaseFactory {
    fun init() {
        val dotenv = dotenv { ignoreIfMissing = true }
        val url = dotenv["DATABASE_URL"] ?: throw IllegalArgumentException("Falta DATABASE_URL en .env")
        val user = dotenv["DATABASE_USER"] ?: throw IllegalArgumentException("Falta DATABASE_USER en .env")
        val password = dotenv["DATABASE_PASSWORD"] ?: throw IllegalArgumentException("Falta DATABASE_PASSWORD en .env")

        val config = HikariConfig().apply {
            jdbcUrl = url
            username = user
            this.password = password
            driverClassName = "org.postgresql.Driver"
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        Database.connect(HikariDataSource(config))
    }
}