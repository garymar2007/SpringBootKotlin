package com.gary.SpringBootKotlin.controller.auth

data class AuthenticationRequest(
    val email: String,
    val password: String
)