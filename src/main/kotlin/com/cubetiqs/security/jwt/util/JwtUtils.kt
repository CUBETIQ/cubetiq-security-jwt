package com.cubetiqs.security.jwt.util

import com.cubetiqs.security.jwt.JwtNotImplementException
import com.cubetiqs.security.jwt.UserNotEnabledException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*
import io.jsonwebtoken.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import java.security.Key
import javax.servlet.http.HttpServletRequest

object JwtUtils {
    private const val AUTHORIZATION_HEADER: String = "Authorization"
    private const val AUTHORIZATION_TYPE = "Bearer "

    private var secretKey: String = "your-secret-key-here-123"
    private var tokenExpiredInMillis: Long = 86400000 // 24 hours
    private var passwordStrength: Int = 10
    private var passwordEncoder: PasswordEncoder? = null
    private var userDetailsService: UserDetailsService? = null

    fun getUserDetailsService(): UserDetailsService {
        if (userDetailsService == null) {
            throw JwtNotImplementException("User details service not implement yet!")
        }

        return userDetailsService!!
    }

    fun setUserDetailsService(service: UserDetailsService) {
        userDetailsService = service
    }

    fun setPasswordStrength(strength: Int) = apply { this.passwordStrength = strength }
    fun passwordEncoder(): PasswordEncoder {
        if (passwordEncoder == null) {
            passwordEncoder = BCryptPasswordEncoder(passwordStrength)
        }

        return passwordEncoder!!
    }

    fun setSecretKey(secret: String) = apply { this.secretKey = secret }
    private fun getSecretKey(): String {
        return Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun setTokenExpiredInMillis(millis: Long) = apply { this.tokenExpiredInMillis = millis }
    private fun getTokenExpiredInMillis() = this.tokenExpiredInMillis

    fun extractToken(request: HttpServletRequest): String? {
        val headerToken = request.getHeader(AUTHORIZATION_HEADER)?.toString() ?: ""
        val isBearerToken = headerToken.trim().lowercase().startsWith(AUTHORIZATION_TYPE.lowercase())

        if (isBearerToken.not()) {
            return null
        }

        val token = headerToken.substring(AUTHORIZATION_TYPE.length)
        val isValidJwtThreePart = token.split(".").size == 3
        if (isValidJwtThreePart.not()) {
            return null
        }

        return token
    }

    private fun validateTokenExpired(claims: Claims): Boolean {
        if (claims.expiration.after(Date())) {
            return true
        }
        return false
    }

    fun decryptTokenWithoutVerify(token: String): Claims {
        class EmptyResolver : SigningKeyResolverAdapter() {
            override fun resolveSigningKey(header: JwsHeader<out JwsHeader<*>>?, claims: Claims?): Key? {
                return null
            }
        }

        val emptyResolveKey = EmptyResolver()
        return (Jwts.parser()
            .setSigningKeyResolver(emptyResolveKey)
            .parse(token)
            .body as? Claims) ?: throw NullPointerException("Token claims is null!")
    }

    fun resolveUserFromToken(token: String?): UsernamePasswordAuthenticationToken? {
        val claims = decryptToken(token) ?: return null
        val isTokenExpired = validateTokenExpired(claims)

        if (isTokenExpired.not()) {
            return null
        }

        val username = claims.subject
        val user = userDetailsService?.loadUserByUsername(username) ?: return null

        if (user.isEnabled.not()) {
            throw UserNotEnabledException("User is disabled!")
        }

        return resolveAuthFromUser(user)
    }

    fun setAuthContext(user: UserDetails) {
        val auth = resolveAuthFromUser(user)
        SecurityContextHolder.getContext().authentication = auth
    }

    private fun resolveAuthFromUser(user: UserDetails): UsernamePasswordAuthenticationToken {
        val auth = UsernamePasswordAuthenticationToken(user.username, user.password, user.authorities)
        auth.details = user
        return auth
    }

    fun encryptToken(user: UserDetails): String {
        val currentDateMillisecond = Date().time + getTokenExpiredInMillis()
        val expiredDate = Date(currentDateMillisecond)

        return Jwts.builder()
            .setSubject(user.username)
            .setIssuedAt(Date())
            .setExpiration(expiredDate)
            .signWith(SignatureAlgorithm.HS256, getSecretKey())
            .compact()
    }

    private fun decryptToken(token: String?): Claims? {
        token ?: return null
        val secretKey = getSecretKey()
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parse(token)
            .body as? Claims
    }
}