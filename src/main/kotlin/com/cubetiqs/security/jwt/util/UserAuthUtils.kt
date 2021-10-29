package com.cubetiqs.security.jwt.util

import org.springframework.security.core.context.SecurityContextHolder

object UserAuthUtils {
    fun getCurrentUsername(): String {
        return SecurityContextHolder.getContext()?.authentication?.name ?: "anonymousUser"
    }

//    fun getCurrentUser() = StaticBeanUtils.getCurrentUser()
}
