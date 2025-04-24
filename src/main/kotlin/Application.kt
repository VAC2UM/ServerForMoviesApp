package com.example

import com.example.login.configureLoginRouting
import com.example.register.configureRegisterRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureDatabase()

    configureSerialization()
    configureRouting()
    configureLoginRouting()
    configureRegisterRouting()
}

fun Application.configureDatabase() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/seriesFilm",
        "org.postgresql.Driver",
        "postgres",
        "271104"
    )
}
