package com.gary.SpringBootKotlin.config

import com.gary.SpringBootKotlin.model.Role
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val authenticationProvider: AuthenticationProvider
) {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter
    ): DefaultSecurityFilterChain =
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/api/v1/auth", "/api/v1/auth/refresh", "/error")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/user")
                    .permitAll()
                    .requestMatchers("/api/v1/user**")
                    .hasRole("ADMIN")
                    // In spring security, hasRole() is the same as hasAuthority(),
                    // but hasRole() function map with Authority without ROLE_ prefix
                    //.hasAnyAuthority(Role.ADMIN.name)
                    .anyRequest()
                    .fullyAuthenticated()
            }
            .sessionManagement {
                // Stateless session - specifies that the session should not be used to store user data,
                // so each request must be authenticated independently.
                // This is typically used in RESTful APIs where each request is stateless.
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
}