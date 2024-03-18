package com.example.kotify.routes

import com.example.kotify.routes.pages.configureMainPage
import com.example.kotify.routes.spotify.play.play
import com.example.kotify.routes.spotify.play.setDevice
import com.example.kotify.routes.spotify.search.playlists
import io.ktor.server.application.*


fun Application.configureRouting() {
    configureStaticRoutes()
    configureSecurity()
    configureMainPage()

    setDevice()
    play()
    playlists()

}
