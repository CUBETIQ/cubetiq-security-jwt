package com.cubetiqs.security.jwt

class JwtNotImplementException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : super(cause)
}