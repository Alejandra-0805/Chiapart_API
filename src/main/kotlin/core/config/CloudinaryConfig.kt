package org.example.core.config

import com.cloudinary.Cloudinary
import io.github.cdimascio.dotenv.dotenv

object CloudinaryConfig {
    private val dotenv = dotenv { ignoreIfMissing = true }
    private val cloudinaryUrl = dotenv["CLOUDINARY_URL"] ?: throw IllegalArgumentException("Falta CLOUDINARY_URL en .env")

    val instance = Cloudinary(cloudinaryUrl)
}