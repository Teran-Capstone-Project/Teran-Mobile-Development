package com.example.teran.data.api.request.auth

data class RegisterRequest(
    val email: String,
    val name: String,
    val password: String
)