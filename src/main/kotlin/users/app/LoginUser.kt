package org.example.users.app

import org.example.core.security.JwtConfig
import org.example.users.domain.repository.UserRepository
import org.mindrot.jbcrypt.BCrypt

class LoginUser(private val repository: UserRepository) {
    fun execute(email: String, passwordRaw: String): Pair<String, String> {
        val user = repository.findByEmail(email) ?: throw Exception("Credenciales incorrectas")

        if (!BCrypt.checkpw(passwordRaw, user.passwordHash)) {
            throw Exception("Credenciales incorrectas")
        }

        val token = JwtConfig.generateToken(user.id, user.username)
        return Pair(token, user.username)
    }
}