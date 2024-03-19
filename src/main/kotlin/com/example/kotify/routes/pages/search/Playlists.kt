package com.example.kotify.routes.pages.search

import com.example.kotify.spotify.api.search.searchForUsersPlaylists
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import kotlinx.html.stream.appendHTML

fun Application.playlists() {
    routing {
        get("/playlists") {

            val playlists = searchForUsersPlaylists(call);

            val response = buildString {
                appendHTML().div{
                    h3("m-2") { +"""My Playlists""" }
                    ul {
                        for (playlist in playlists.items) {
                            li {
                                div("flex cursor-pointer m-2") {
                                    attributes["hx-get"] = "/play?uri="+playlist.uri

                                    img(classes = "rounded-md size-10 mr-2") {
                                        src = playlist.images[0].url ?: ""
                                        alt = "Trulli"
                                    }
                                    div {
                                        + (playlist.name ?: "")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            call.respond(response)
        }
    }
}