package com.cubetiqs.security.jwt

class UserNotEnabledException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : super(cause)
}