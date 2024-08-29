package com.gary.SpringBootKotlin.respository

import com.gary.SpringBootKotlin.model.Role
import com.gary.SpringBootKotlin.model.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepository(private val encoder: PasswordEncoder) {
    private var users = mutableListOf(
        User(id = UUID.randomUUID(), email = "email1", password = encoder.encode("pass1"), role = Role.ROLE_USER),
        User(id = UUID.randomUUID(), email = "email2", password = encoder.encode("pass2"), role = Role.ROLE_ADMIN),
        User(id = UUID.randomUUID(), email = "email3", password = encoder.encode("pass3"), role = Role.ROLE_USER),
    )

    fun getAllUsers(): List<User> = users

    fun getUserById(id: String): User? = users.firstOrNull { it.id.toString() == id }

    fun getUserByEmail(email: String): User? = users.firstOrNull { it.email == email }

    fun saveUser(user: User): User {
        val updated = user.copy(password = encoder.encode(user.password))
        users.add(updated)
        return updated
    }

    fun deleteUser(id: String): Boolean {
        val foundUser = users.firstOrNull { it.id.toString() == id }
        return foundUser?.let {
            users.remove(it)
        } ?: false
    }

    fun updateUser(id: String, user: User): User? {
        val index = users.indexOfFirst { it.id.toString() == id }
        if (index == -1) {
            return null
        }
        users[index] = user
        return user
    }
}