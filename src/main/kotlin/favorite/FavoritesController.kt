package com.example.favorite

import com.example.database.favorites.FavoriteDTO
import com.example.database.favorites.Favorites
import com.example.database.tokens.Tokens
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class FavoritesController(private val call: ApplicationCall) {
    suspend fun addFavorite() {
        val token = call.request.headers["Authorization"] ?: ""
        val userLogin = Tokens.getLoginByToken(token) ?: run {
            call.respond(HttpStatusCode.Unauthorized, "Invalid token")
            return
        }

        val receive = try {
            call.receive<FavoriteReceiveRemote>()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid request body")
            return
        }

        val success = Favorites.insert(FavoriteDTO(userLogin, receive.movieId))

        if (success) {
            call.respond(HttpStatusCode.OK, "Movie added to favorites")
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Failed to add movie to favorites")
        }
    }

    suspend fun getFavorites() {
        val token = call.request.headers["Authorization"] ?: ""
        val userLogin = Tokens.getLoginByToken(token) ?: run {
            call.respond(HttpStatusCode.Unauthorized, "Invalid token")
            return
        }

        val favorites = Favorites.getFavoritesByUser(userLogin)
        call.respond(FavoriteResponseRemote(favorites))
    }
}
