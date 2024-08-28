package com.gary.SpringBootKotlin.respository

import com.gary.SpringBootKotlin.model.Role
import com.gary.SpringBootKotlin.model.User
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepository {
    private var users = mutableListOf(
        User(id = UUID.randomUUID(), email = "email1", password = "pass1", role = Role.USER),
        User(id = UUID.randomUUID(), email = "email2", password = "pass2", role = Role.ADMIN),
        User(id = UUID.randomUUID(), email = "email3", password = "pass3", role = Role.USER),
    )

    fun getAllUsers(): List<User> = users

    fun getUserById(id: String): User? = users.firstOrNull { it.id.toString() == id }

    fun getUserByEmail(email: String): User? = users.firstOrNull { it.email == email }

    fun saveUser(user: User): User {
        users.add(user)
        return user
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