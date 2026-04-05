package org.example.products.infra.cloudinary

import com.cloudinary.utils.ObjectUtils
import org.example.core.config.CloudinaryConfig
import org.example.products.domain.repository.ImageStorage

class CloudinaryService : ImageStorage {
    override fun uploadImage(imageBytes: ByteArray): String {
        val uploadResult = CloudinaryConfig.instance.uploader().upload(
            imageBytes,
            ObjectUtils.asMap("folder", "chiapart")
        )
        return uploadResult["secure_url"] as String
    }
}