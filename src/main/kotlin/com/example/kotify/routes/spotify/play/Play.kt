package com.example.kotify.routes.spotify.play

import com.example.kotify.spotify.api.player.playUri
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.play() {
    routing {
        get("/play") {
            val uri = call.request.queryParameters["uri"] ?: "" //TODO: handle
            call.playUri(uri)
            call.respond(HttpStatusCode.OK)
        }
    }
}