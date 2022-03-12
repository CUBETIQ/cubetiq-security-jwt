package com.cubetiqs.security.jwt.exception

import com.cubetiqs.enterprise.data.exception.GeneralDataException

class ExpiredJwtTokenException(
    message: String? = "",
): GeneralDataException(message)