package com.gary.SpringBootKotlin.controller.auth

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String
)