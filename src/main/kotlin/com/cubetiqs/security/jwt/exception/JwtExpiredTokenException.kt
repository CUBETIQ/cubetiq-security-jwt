package com.cubetiqs.security.jwt.exception

class JwtExpiredTokenException(
    message: String? = "Expired JWT token!"
) : BaseJwtException(message)