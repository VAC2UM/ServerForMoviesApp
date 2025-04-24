package com.example.utils

object Validators {
    private val emailRegex = Regex(
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
    )

    fun String.isValidEmail(): Boolean = emailRegex.matches(this)
}