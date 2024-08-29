package com.gary.SpringBootKotlin.service

import com.gary.SpringBootKotlin.config.JwtProperties
import com.gary.SpringBootKotlin.controller.auth.AuthenticationRequest
import com.gary.SpringBootKotlin.controller.auth.AuthenticationResponse
import com.gary.SpringBootKotlin.controller.auth.RefreshTokenRequest
import com.gary.SpringBootKotlin.controller.auth.TokenResponse
import com.gary.SpringBootKotlin.respository.RefreshTokenRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun authenticate(authRequest: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(authRequest.email, authRequest.password)
        )

        val user = userDetailsService.loadUserByUsername(authRequest.email)
        val accessToken = generateAccessToken(user, jwtProperties.accessTokenExpiration)
        val refreshToken = generateAccessToken(user, jwtProperties.refreshTokenExpiration)

        refreshTokenRepository.save(refreshToken, user)

        return AuthenticationResponse(accessToken, refreshToken)
    }

    fun refreshAccessToken(refreshToken: String): String? {
        val email = tokenService.extractEmail(refreshToken)

        return email?.let { email ->
            val currentUserDetails = userDetailsService.loadUserByUsername(email)
            val refreshTokenUserDetails = refreshTokenRepository.findUserDetailsByToken(refreshToken)

            if (!tokenService.isExpired(refreshToken) && currentUserDetails.username == refreshTokenUserDetails?.username) {
                generateAccessToken(currentUserDetails, jwtProperties.accessTokenExpiration)
            } else {
                null
            }
        }
    }

    private fun generateAccessToken(userDetails: UserDetails, tokenExpiration: Long): String =
        tokenService.generate(
            userDetails,
            Date(System.currentTimeMillis() + tokenExpiration)
        )
}