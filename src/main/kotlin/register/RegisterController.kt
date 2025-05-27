package com.example.register

import com.example.database.tokens.TokenDTO
import com.example.database.tokens.Tokens
import com.example.database.users.UserDTO
import com.example.database.users.Users
import com.example.utils.PasswordHasher
import com.example.utils.Validators.isValidEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.util.UUID

class RegisterController(val call: ApplicationCall) {
    suspend fun registerNewUser() {
        val registerRecieveRemote = call.receive<RegisterRecieveRemote>()

        if (!registerRecieveRemote.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Email is not valid")
            return
        }

        val userDTO = Users.fetchUser(registerRecieveRemote.login)
        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
            return
        }

        val passwordHash = PasswordHasher.hashPassword(registerRecieveRemote.password)

        try {
            Users.insert(
                UserDTO(
                    login = registerRecieveRemote.login,
                    password = passwordHash,
                    email = registerRecieveRemote.email
                )
            )
        } catch (e: ExposedSQLException) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
            return
        }

        val token = UUID.randomUUID().toString()
        Tokens.insert(
            TokenDTO(
                login = registerRecieveRemote.login,
                token = token
            )
        )
        call.respond(RegisterResponseRemote(token = token))
    }
}