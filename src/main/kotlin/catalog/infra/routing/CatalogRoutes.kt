package org.example.catalog.infra.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.catalog.app.GetCategories
import org.example.catalog.app.GetRegions

fun Application.catalogRoutes(getCategories: GetCategories, getRegions: GetRegions) {
    routing {
        route("/api/v1/catalog") {
            get("/categories") {
                call.respond(getCategories.execute())
            }
            get("/regions") {
                call.respond(getRegions.execute())
            }
        }
    }
}