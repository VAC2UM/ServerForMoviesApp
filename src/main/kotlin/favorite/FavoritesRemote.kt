package com.example.favorite

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteReceiveRemote(
    val movieId: Int
)

@Serializable
data class FavoriteResponseRemote(
    val favorites: List<Int>
)