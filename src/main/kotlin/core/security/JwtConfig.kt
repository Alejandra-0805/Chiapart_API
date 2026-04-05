package org.example.core.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.github.cdimascio.dotenv.dotenv
import java.util.*

object JwtConfig {
    private val dotenv = dotenv { ignoreIfMissing = true }
    private val secret = dotenv["JWT_SECRET"] ?: throw IllegalArgumentException("Falta JWT_SECRET en .env")
    private const val issuer = "chiapart-api"
    private const val validityInMs = 36_000_00 * 24 * 7 // 7 días

    val algorithm: Algorithm = Algorithm.HMAC256(secret)

    val verifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateToken(userId: Int, username: String): String {
        return JWT.create()
            .withIssuer(issuer)
            .withClaim("id", userId)
            .withClaim("username", username)
            .withExpiresAt(Date(System.currentTimeMillis() + validityInMs))
            .sign(algorithm)
    }
}