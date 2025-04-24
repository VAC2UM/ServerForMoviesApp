package com.example

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class Test(
    val text: String
)

fun Application.configureRouting() {
    routing {
        get("/") {
            val testObject = Test(text = "Hello World!")
            call.respondText(testObject.toString())
        }
    }
}
