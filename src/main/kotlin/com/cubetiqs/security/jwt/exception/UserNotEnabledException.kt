package com.cubetiqs.security.jwt.exception

class UserNotEnabledException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : super(cause)
}