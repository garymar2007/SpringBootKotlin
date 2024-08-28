package com.gary.SpringBootKotlin.controller

import com.gary.SpringBootKotlin.dto.UserRequest
import com.gary.SpringBootKotlin.dto.UserResponse
import com.gary.SpringBootKotlin.model.Role
import com.gary.SpringBootKotlin.model.User
import com.gary.SpringBootKotlin.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService,
) {
    @GetMapping
    fun listAll(): List<UserResponse> =
        userService.getAllUsers()
            .map {it.toResponse()}

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String) : UserResponse =
        userService.getUserById(id)?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User uuid not found")

    @GetMapping("/email/{email}")
    fun getUserByEmail(@PathVariable("email") email: String) : UserResponse =
        userService.getUserByEmail(email)?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User email not found")

    @PostMapping
    fun saveUser(@RequestBody userRequest: UserRequest): UserResponse = userService.saveUser(
        user = userRequest.toModel()
    )
        .toResponse()
        ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create a User")

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String) : ResponseEntity<Boolean> {
        val isDeleted = userService.deleteUser(id)
        return if (isDeleted) ResponseEntity.ok(true)
        else ResponseEntity.status(HttpStatus.NOT_FOUND).body(false)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: String, @RequestBody userRequest: UserRequest) : UserResponse =
        userService.updateUser(id, userRequest.toModel())?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User uuid not found")

    private fun UserRequest.toModel() = User(
        id = UUID.randomUUID(),
        email = this.email,
        password = this.password,
        role = Role.USER
    )

    private fun User.toResponse() = UserResponse(
        UUId = this.id,
        email = this.email
    )
}