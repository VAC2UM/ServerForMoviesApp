package com.example.utils

import java.security.MessageDigest
import java.util.*

object PasswordHasher {
    private const val HASH_ALGORITHM = "SHA-256"

    fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance(HASH_ALGORITHM)
        val hashBytes = digest.digest(password.toByteArray())
        return Base64.getEncoder().encodeToString(hashBytes)
    }

    fun verifyPassword(password: String, storedHash: String): Boolean {
        val newHash = hashPassword(password)
        return newHash == storedHash
    }
}