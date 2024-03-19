package com.example.kotify.routes.pages

import com.example.kotify.security.UserSession
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.html.*


class SpotifyWebPlayer(initialAttributes: Map<String, String>, override val consumer: TagConsumer<*>) :
    HTMLTag("spotify-web-player", consumer, initialAttributes, inlineTag = false, emptyTag = false)

fun DIV.spotifyWebPlayer(block: SpotifyWebPlayer.() -> Unit = {}) {
    SpotifyWebPlayer(attributesMapOf(),  consumer).visit(block)
}

fun Application.configureMainPage() {
    routing {
        get("/") {
            call.respondHtml {
                body("flex justify-center items-center h-screen bg-gray-100") {
                    div("bg-neutral-00") {
                        a(href="/login") {
                            button(classes = "bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded-full focus:outline-none focus:shadow-outline") {
                                + """Login"""
                            }
                        }
                    }
                }
            }
        }
        get("/home") {
            val session = call.sessions.get<UserSession>()
            call.respondHtml {
                head {
                    link {
                        href = "http://localhost:8888/static/css/output.css"
                        rel = "stylesheet"
                    }
                    script {
                        src = "https://unpkg.com/htmx.org@1.9.10"
                    }
                    script {
                        src = "https://sdk.scdn.co/spotify-player.js"
                    }
                    script {
                        src = "http://localhost:8888/static/js/player.js"
                    }
                    script {
                        unsafe {
                            + """
                            tailwind.config = {
                              theme: {
                                extend: {
                                  colors: {
                                    clifford: '#da373d',
                                  }
                                }
                              }
                            }
                            """.trimIndent()
                        }
                    }
                }
                body("overflow-hidden bg-neutral-700") {
                    script {
                        unsafe {
                            +"""
                                document.addEventListener("DOMContentLoaded", function() {
                                    document.getElementById("player").addEventListener("player-ready", (e) => {
                                        console.log("player ready")
                                        fetch("/setDevice?id="+e.detail)
                                    });
                                });
                            """
                        }
                    }
                    div("flex h-screen overflow-hidden") {
                        div("flex-shrink-0 w-1/3 overflow-y-auto bg-neutral-700 text-white") {
                            div {
                                attributes["hx-get"] = "/playlists"
                                attributes["hx-trigger"] = "load"
                                id = "playlists"
                            }
                        }
                        div("flex-shrink-0 w-2/3 overflow-y-auto bg-neutral-700 text-white m-2") {
                            input(classes = "bg-neutral-900 h-10 px-5 pr-16 rounded-full text-sm focus:outline-none") {
                                type = InputType.text
                                name = "search"
                                attributes["hx-post"] = "/search"
                                attributes["hx-target"] = "#searchResult"
                                attributes["hx-trigger"] = "input changed delay:500ms, search"
                                placeholder = "Search"
                            }
                            div {
                                id = "searchResult"
                            }
                        }
                    }
                    div("fixed bottom-0 w-full h-200 bg-neutral-900") {
                        spotifyWebPlayer {
                            attributes["id"] = "player"
                            attributes["accesstoken"] = session?.accessToken ?: "NO SESSION!"
                        }
                    }
                }
            }

        }


    }
}