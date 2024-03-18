package com.example.kotify

import com.example.kotify.plugins.configureSerialization
import com.example.kotify.routes.configureRouting
import com.example.kotify.routes.configureSecurity
import com.example.kotify.routes.configureStaticRoutes
import io.github.cdimascio.dotenv.Dotenv
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val dotenv = Dotenv.configure().load()
    embeddedServer(Netty, port = dotenv.get("PORT", "8888").toInt(), host = dotenv.get("APP_ENV", "localhost"), module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureStaticRoutes()
    configureSerialization()
    configureSecurity()
    configureRouting()
}
