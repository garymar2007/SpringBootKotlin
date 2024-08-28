package com.gary.SpringBootKotlin.service

import com.gary.SpringBootKotlin.model.User
import com.gary.SpringBootKotlin.respository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun getAllUsers() = userRepository.getAllUsers()

    fun getUserById(id: String) = userRepository.getUserById(id)

    fun getUserByEmail(email: String) = userRepository.getUserByEmail(email)

    fun saveUser(user: User) = userRepository.saveUser(user)

    fun deleteUser(id: String) = userRepository.deleteUser(id)

    fun updateUser(id: String, user: User) = userRepository.updateUser(id, user)
}