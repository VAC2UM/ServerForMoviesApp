package com.example.database.tokens

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table("tokens") {
    private val login = Tokens.varchar("login", 25)
    private val id = Tokens.varchar("id", 50)
    private val token = Tokens.varchar("token", 50)

    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[login] = tokenDTO.login
                it[token] = tokenDTO.token
            }
        }
    }

    fun getLoginByToken(token: String): String? {
        return transaction {
            Tokens.select(Tokens.login)
                .where { Tokens.token eq token }
                .singleOrNull()
                ?.get(Tokens.login)
        }
    }
}