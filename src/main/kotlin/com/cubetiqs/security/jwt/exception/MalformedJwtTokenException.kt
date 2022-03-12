package com.cubetiqs.security.jwt.exception

import com.cubetiqs.enterprise.data.exception.GeneralDataException

class MalformedJwtTokenException(
    message: String? = "",
): GeneralDataException(message)