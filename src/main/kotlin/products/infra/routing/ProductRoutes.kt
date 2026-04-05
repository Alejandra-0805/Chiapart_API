package org.example.products.infra.routing

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.products.app.*

fun Application.productRoutes(
    createProduct: CreateProduct,
    getProducts: GetProducts,
    getProductById: GetProductById,
    getMyProducts: GetMyProducts,
    updateProduct: UpdateProduct,
    deleteProduct: DeleteProduct
) {
    routing {
        route("/api/v1/products") {

            // GET PÚBLICO: Ver todos los productos (Catálogo general)
            get {
                val search = call.request.queryParameters["search"]
                val categoryId = call.request.queryParameters["category"]?.toIntOrNull()
                val regionId = call.request.queryParameters["region"]?.toIntOrNull()
                call.respond(getProducts.execute(search, categoryId, regionId))
            }

            get("/{id}") {
                val productId = call.parameters["id"]?.toIntOrNull()
                if (productId == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                    return@get
                }

                val product = getProductById.execute(productId)
                if (product != null) {
                    call.respond(HttpStatusCode.OK, product)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Producto no encontrado"))
                }
            }

            // RUTAS PROTEGIDAS
            authenticate("auth-jwt") {

                // GET PRIVADO: Ver solo mis productos
                get("/me") {
                    val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("id").asInt()
                    call.respond(getMyProducts.execute(userId))
                }

                // POST: Crear producto
                post {
                    val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("id").asInt()
                    var nombre = ""; var precio = 0.0; var descripcion = ""; var categoriaId = 0; var regionId = 0
                    var imageBytes: ByteArray? = null

                    call.receiveMultipart().forEachPart { part ->
                        when (part) {
                            is PartData.FormItem -> {
                                when (part.name) {
                                    "nombre" -> nombre = part.value
                                    "precio" -> precio = part.value.toDoubleOrNull() ?: 0.0
                                    "descripcion" -> descripcion = part.value
                                    "categoriaId" -> categoriaId = part.value.toIntOrNull() ?: 0
                                    "regionId" -> regionId = part.value.toIntOrNull() ?: 0
                                }
                            }
                            is PartData.FileItem -> {
                                if (part.name == "imagen") imageBytes = part.streamProvider().readBytes()
                            }
                            else -> {}
                        }
                        part.dispose()
                    }

                    if (imageBytes == null || nombre.isBlank() || precio <= 0.0) {
                        call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Faltan datos o la imagen"))
                        return@post
                    }

                    val result = createProduct.execute(nombre, precio, descripcion, categoriaId, regionId, userId, imageBytes!!)
                    // SOLUCIÓN AL ERROR 500: result.id.toString()
                    call.respond(HttpStatusCode.Created, mapOf("message" to "Producto creado", "id" to result.id.toString()))
                }

                // PUT: Actualizar producto
                put("/{id}") {
                    val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("id").asInt()
                    val productId = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)

                    var nombre = ""; var precio = 0.0; var descripcion = ""; var categoriaId = 0; var regionId = 0
                    var imageBytes: ByteArray? = null

                    call.receiveMultipart().forEachPart { part ->
                        when (part) {
                            is PartData.FormItem -> {
                                when (part.name) {
                                    "nombre" -> nombre = part.value
                                    "precio" -> precio = part.value.toDoubleOrNull() ?: 0.0
                                    "descripcion" -> descripcion = part.value
                                    "categoriaId" -> categoriaId = part.value.toIntOrNull() ?: 0
                                    "regionId" -> regionId = part.value.toIntOrNull() ?: 0
                                }
                            }
                            is PartData.FileItem -> {
                                if (part.name == "imagen") {
                                    val bytes = part.streamProvider().readBytes()
                                    if (bytes.isNotEmpty()) imageBytes = bytes
                                }
                            }
                            else -> {}
                        }
                        part.dispose()
                    }

                    val updated = updateProduct.execute(productId, userId, nombre, precio, descripcion, categoriaId, regionId, imageBytes)
                    if (updated) {
                        call.respond(HttpStatusCode.OK, mapOf("message" to "Producto actualizado"))
                    } else {
                        call.respond(HttpStatusCode.NotFound, mapOf("error" to "No se pudo actualizar"))
                    }
                }

                // DELETE: Eliminar producto
                delete("/{id}") {
                    val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("id").asInt()
                    val productId = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)

                    val deleted = deleteProduct.execute(productId, userId)
                    if (deleted) call.respond(HttpStatusCode.OK, mapOf("message" to "Producto eliminado"))
                    else call.respond(HttpStatusCode.NotFound, mapOf("error" to "No encontrado o sin permiso"))
                }
            }
        }
    }
}