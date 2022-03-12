package com.cubetiqs.security.jwt.exception

import com.cubetiqs.enterprise.data.exception.GeneralDataException

class SignatureTokenException(
    message: String? = "",
): GeneralDataException(message)