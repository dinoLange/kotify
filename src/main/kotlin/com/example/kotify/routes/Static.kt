package com.example.kotify.routes

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureStaticRoutes() {
    routing {
        staticResources("/static", "static") {
        }
    }
}