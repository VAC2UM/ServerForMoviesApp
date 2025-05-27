package com.example.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table("users") {
    val login = varchar("login", 25)
    val password = varchar("password", 64)
    val email = varchar("email", 25)

    fun insert(userDTO: UserDTO) {
        transaction {
            insert {
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[email] = userDTO.email
            }
        }
    }

    fun fetchUser(login: String): UserDTO? {
        return try {
            transaction {
                Users.selectAll().where { Users.login eq login }.singleOrNull()?.let { row ->
                    UserDTO(
                        login = row[Users.login],
                        password = row[password],
                        email = row[email]
                    )
                }
            }
        } catch (e: Exception) {
            null
        }
    }
}