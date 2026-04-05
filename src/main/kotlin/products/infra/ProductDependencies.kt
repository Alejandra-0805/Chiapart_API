package org.example.products.infra

import io.ktor.server.application.*
import org.example.products.app.*
import org.example.products.infra.cloudinary.CloudinaryService
import org.example.products.infra.persistence.PostgresProductRepository
import org.example.products.infra.routing.productRoutes

fun Application.initProductsModule() {
    val repository = PostgresProductRepository()
    val imageStorage = CloudinaryService()

    productRoutes(
        createProduct = CreateProduct(repository, imageStorage),
        getProducts = GetProducts(repository),
        getMyProducts = GetMyProducts(repository),
        updateProduct = UpdateProduct(repository, imageStorage),
        deleteProduct = DeleteProduct(repository)
    )
}