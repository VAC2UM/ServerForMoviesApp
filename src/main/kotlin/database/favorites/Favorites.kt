package com.example.database.favorites

import com.example.database.users.Users
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Favorites : Table("favorites") {
    val id = integer("id").autoIncrement()
    val userLogin = varchar("user_login", 25).references(Users.login)
    val movieId = integer("movie_id")
    val movieTitle = varchar("movie_title", 255)
    val moviePoster = varchar("movie_poster", 512).nullable()

    override val primaryKey = PrimaryKey(id)

    fun insert(favoriteDTO: FavoriteDTO): Boolean {
        return try {
            transaction {
                insert {
                    it[userLogin] = favoriteDTO.userLogin
                    it[movieId] = favoriteDTO.movieId
                    it[movieTitle] = favoriteDTO.movieTitle
                    it[moviePoster] = favoriteDTO.moviePoster
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getFavoritesByUser(login: String): List<FavoriteDTO> {
        return transaction {
            Favorites.selectAll()
                .where { Favorites.userLogin eq login }
                .map {
                    FavoriteDTO(
                        userLogin = it[userLogin],
                        movieId = it[movieId],
                        movieTitle = it[movieTitle],
                        moviePoster = it[moviePoster]
                    )
                }
        }
    }
}