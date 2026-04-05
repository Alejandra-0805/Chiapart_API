package org.example.users.infra

import io.ktor.server.application.*
import org.example.users.app.LoginUser
import org.example.users.app.RegisterUser
import org.example.users.infra.persistence.PostgresUserRepository
import org.example.users.infra.routing.userRoutes

fun Application.initUserModule() {
    val repository = PostgresUserRepository()
    userRoutes(
        registerUser = RegisterUser(repository),
        loginUser = LoginUser(repository)
    )
}