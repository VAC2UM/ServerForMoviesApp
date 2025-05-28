package com.example.database.favorites

import com.example.database.users.Users
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object Favorites : Table("favorites") {
    val id = integer("id").autoIncrement()
    val userLogin = varchar("user_login", 25).references(Users.login)
    val movieId = integer("movie_id")

    override val primaryKey = PrimaryKey(id)

    fun insert(favoriteDTO: FavoriteDTO): Boolean {
        return try {
            transaction {
                insert {
                    it[userLogin] = favoriteDTO.userLogin
                    it[movieId] = favoriteDTO.movieId
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getFavoritesByUser(login: String): List<Int> {
        return transaction {
            Favorites.select(Favorites.movieId)
                .where { Favorites.userLogin eq login }
                .map { it[Favorites.movieId] }
        }
    }
}
