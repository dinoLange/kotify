package com.example.kotify.spotify.api.player

import com.example.kotify.routes.UserSession
import com.google.gson.annotations.SerializedName
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class TrackInfo(
    @SerializedName("context_uri"     ) val uri: String,
    @SerializedName("position_ms"     ) val position: Int
)

suspend fun ApplicationCall.playUri(uri: String) {

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    val session = this.sessions.get<UserSession>() ?: throw IllegalArgumentException() // TODO: better exception / error value
    val token = session.accessToken
    val deviceId = session.deviceId
    val trackInfo = TrackInfo(uri, 0)

    val response = client.put("https://api.spotify.com/v1/me/player/play?device_id=$deviceId") {
        headers {
            append("Authorization", "Bearer $token")
            append("Content-Type", "application/json")
        }
        setBody(trackInfo)
    }

    println(response)

}