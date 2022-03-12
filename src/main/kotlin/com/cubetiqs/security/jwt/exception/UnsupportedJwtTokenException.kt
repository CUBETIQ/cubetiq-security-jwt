package com.cubetiqs.security.jwt.exception

import com.cubetiqs.enterprise.data.exception.GeneralDataException

class UnsupportedJwtTokenException(
    message: String? = "",
): GeneralDataException(message)