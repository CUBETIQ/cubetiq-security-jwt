package com.cubetiqs.security.jwt.exception

import com.cubetiqs.enterprise.data.exception.GeneralDataException

class EmptyJwtClaimsException(
    message: String? = "",
): GeneralDataException(message)