package com.example.favorite

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteReceiveRemote(
    val movieId: Int,
    val movieTitle: String,
    val moviePoster: String?
)

@Serializable
data class FavoriteResponseRemote(
    val favorites: List<FavoriteItemRemote>
)

@Serializable
data class FavoriteItemRemote(
    val movieId: Int,
    val movieTitle: String,
    val moviePoster: String?
)