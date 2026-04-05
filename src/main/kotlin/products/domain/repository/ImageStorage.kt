package org.example.products.domain.repository

interface ImageStorage {
    fun uploadImage(imageBytes: ByteArray): String
}