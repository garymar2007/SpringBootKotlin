package com.gary.SpringBootKotlin.service

import com.gary.SpringBootKotlin.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    jwtProperties: JwtProperties
) {
    private val secreteKey = Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )

    fun generate(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String =
        Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expirationDate)
            .add(additionalClaims)
            .and()
            .signWith(secreteKey)
            .compact()

    fun extractEmail(token: String): String? =
        getAllClaims(token)
            .subject

    fun isExpired(token: String): Boolean =
        getAllClaims(token)
            .expiration
            .before(Date(System.currentTimeMillis()))

    fun isValid(token: String, userDetails: UserDetails): Boolean {
        val email = extractEmail(token)
        return email == userDetails.username && !isExpired(token)
    }

    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parser()
            .verifyWith(secreteKey)
            .build()

        return parser.parseSignedClaims(token)
            .payload
    }
}

//https://www.youtube.com/watch?v=iqkt9ip567A&list=PLvN8k8yxjoeud4ESoB-wjiieqYGaDVqPR&index=7