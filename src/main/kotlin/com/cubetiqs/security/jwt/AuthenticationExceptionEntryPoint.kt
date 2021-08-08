package com.cubetiqs.security.jwt

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException

class AuthenticationExceptionEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        authException.printStackTrace()

        val responseBody = mapOf(
            "status" to HttpStatus.FORBIDDEN,
            "message" to authException.message,
        )

        response.status = HttpStatus.FORBIDDEN.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        val writer = response.writer!!
        writer.print(responseBody)
        writer.flush()
        writer.close()
    }
}