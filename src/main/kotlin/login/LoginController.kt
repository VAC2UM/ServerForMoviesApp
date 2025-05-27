package com.example.login

import com.example.database.tokens.TokenDTO
import com.example.database.tokens.Tokens
import com.example.database.users.Users
import com.example.utils.PasswordHasher
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.UUID

class LoginController(private val call: ApplicationCall) {
    suspend fun performLogin() {
        val recieve = call.receive<LoginRecieveRemote>()
        val userDTO = Users.fetchUser(recieve.login)

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else {
            if (PasswordHasher.verifyPassword(recieve.password, userDTO.password)) {
                val token = UUID.randomUUID().toString()
                Tokens.insert(
                    TokenDTO(
                        login = recieve.login,
                        token = token
                    )
                )
                call.respond(LoginResponseRemote(token = token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }
    }
}