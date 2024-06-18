package com.example.teran.data.model

data class User(
    var token: String? = null,
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var profilePicture: String? = null,
    var role: String? = null
)