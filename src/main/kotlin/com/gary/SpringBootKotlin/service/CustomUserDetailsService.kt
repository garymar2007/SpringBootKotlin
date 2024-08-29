package com.gary.SpringBootKotlin.service

import com.gary.SpringBootKotlin.respository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = com.gary.SpringBootKotlin.model.User

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.getUserByEmail(username)
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("User not found!")
    }

    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .authorities(this.role.name)
            .build()

}