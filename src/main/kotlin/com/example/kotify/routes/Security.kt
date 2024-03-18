package com.example.kotify.routes

import io.github.cdimascio.dotenv.Dotenv
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Application.configureSecurity() {
    install(Sessions) {
        cookie<UserSession>("user_session", SessionStorageMemory())
    }

    val redirects = mutableMapOf<String, String>()
    install(Authentication) {

        oauth("auth-oauth-spotify") {
            val dotenv = Dotenv.configure().load()
            urlProvider = { dotenv.get("CALLBACK-URL", "http://localhost:8888/callback") }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "spotify",
                    authorizeUrl = "https://accounts.spotify.com/authorize",
                    accessTokenUrl = "https://accounts.spotify.com/api/token",
                    requestMethod = HttpMethod.Post,
                    clientId = dotenv.get("SPOTIFY_CLIENT_ID"),
                    clientSecret = dotenv.get("SPOTIFY_CLIENT_SECRET"),
                    defaultScopes = listOf("streaming user-read-private user-read-email user-read-playback-state")
                )
            }
            client = HttpClient(Apache)
        }
    }
    routing {
        authenticate("auth-oauth-spotify") {
                    get("login") {
                        // Redirects to 'authorizeUrl' automatically
                    }
        
                    get("/callback") {
                        val currentPrincipal: OAuthAccessTokenResponse.OAuth2? = call.principal()
                        // redirects home if the url is not found before authorization
                        currentPrincipal?.let { principal ->
                            principal.state?.let { state ->
                                call.sessions.set(UserSession(state, principal.accessToken, ""))
                                redirects[state]?.let { redirect ->
                                    call.respondRedirect(redirect)
                                    return@get
                                }
                            }
                        }
                        call.respondRedirect("/home")
                    }
                }
    }
}

data class UserSession(val state: String, val accessToken: String, var deviceId: String)
