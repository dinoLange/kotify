package com.example.kotify.routes.spotify.play

import com.example.kotify.security.UserSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Application.setDevice() {
    routing {
        get ("/setDevice"){
            val deviceId = call.request.queryParameters["id"] ?: "" // TODO: handle
            val session = this.context.sessions.get<UserSession>()
            call.sessions.set(session?.copy(deviceId=deviceId))
            call.respond(HttpStatusCode.OK)
        }
    }

}