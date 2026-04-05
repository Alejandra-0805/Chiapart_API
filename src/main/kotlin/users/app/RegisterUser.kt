package org.example.users.app

import org.example.users.domain.models.User
import org.example.users.domain.repository.UserRepository
import org.mindrot.jbcrypt.BCrypt

class RegisterUser(private val repository: UserRepository) {
    fun execute(email: String, passwordRaw: String, username: String): User {
        if (repository.findByEmail(email) != null) {
            throw Exception("El correo ya está registrado")
        }
        val hashedPw = BCrypt.hashpw(passwordRaw, BCrypt.gensalt(12))
        val newUser = User(email = email, passwordHash = hashedPw, username = username)
        return repository.save(newUser)
    }
}