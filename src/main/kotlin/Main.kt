package org.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.example.core.config.DatabaseFactory
import org.example.core.plugins.configureSecurity
import org.example.core.plugins.configureSerialization
import org.example.users.infra.initUserModule
import org.example.catalog.infra.initCatalogModule
import org.example.products.infra.initProductsModule

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()

    configureSerialization()
    configureSecurity()

    initUserModule()
    initCatalogModule()
    initProductsModule()
}