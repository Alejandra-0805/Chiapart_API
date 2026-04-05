package org.example.users.infra.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.users.app.LoginUser
import org.example.users.app.RegisterUser
import org.example.users.app.dto.LoginRequest
import org.example.users.app.dto.RegisterRequest
import org.example.users.app.dto.AuthResponse

fun Application.userRoutes(registerUser: RegisterUser, loginUser: LoginUser) {
    routing {
        route("/api/v1/users") {
            post("/register") {
                try {
                    val req = call.receive<RegisterRequest>()
                    registerUser.execute(req.email, req.passwordRaw, req.username)
                    call.respond(HttpStatusCode.Created, mapOf("message" to "Usuario creado exitosamente"))
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to (e.message ?: "Error al registrar")))
                }
            }

            post("/login") {
                try {
                    val req = call.receive<LoginRequest>()
                    val (token, username) = loginUser.execute(req.email, req.passwordRaw)
                    call.respond(HttpStatusCode.OK, AuthResponse(token, username))
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Credenciales incorrectas"))
                }
            }
        }
    }
}