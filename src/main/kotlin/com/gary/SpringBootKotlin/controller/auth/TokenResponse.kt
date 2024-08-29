package com.gary.SpringBootKotlin.controller.auth

data class TokenResponse(
    val token: String,
    val expiration: Long
)
