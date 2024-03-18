package com.example.kotify.spotify.api.search

import com.example.kotify.routes.UserSession
import com.example.kotify.spotify.api.models.Playlist
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import kotlinx.serialization.json.Json

suspend fun searchForUsersPlaylists(call: ApplicationCall): Playlist {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }
    val session = call.sessions.get<UserSession>() ?: throw IllegalArgumentException() // TODO: better exception / error value
    val token = session.accessToken
    try {
        val playlists: Playlist = client.get("https://api.spotify.com/v1/me/playlists") {
            headers {
                append("Authorization", "Bearer $token")
            }
        }.body()

        return playlists
    } catch (e: Exception) {
        println("Error: ${e.message}")
    } finally {
        client.close() // Close the client when done
    }
    return Playlist()
}