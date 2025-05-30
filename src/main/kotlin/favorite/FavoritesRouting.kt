package com.example.favorite

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureFavoritesRouting() {
    routing {
        post("/favorites/add") {
            val favoritesController = FavoritesController(call)
            favoritesController.addFavorite()
        }

        get("/favorites") {
            val favoritesController = FavoritesController(call)
            favoritesController.getFavorites()
        }
    }
}