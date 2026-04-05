package org.example.users.app.dto

import kotlinx.serialization.Serializable

@Serializable data class RegisterRequest(val email: String, val passwordRaw: String, val username: String)
@Serializable data class LoginRequest(val email: String, val passwordRaw: String)
@Serializable data class AuthResponse(val token: String, val username: String)