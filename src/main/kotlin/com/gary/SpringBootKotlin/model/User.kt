package com.gary.SpringBootKotlin.model

import java.util.*

data class User(
    val id: UUID,
    val email: String,
    val password: String,
    val role: Role
)

enum class Role {
    ROLE_USER,
    ROLE_ADMIN
}
