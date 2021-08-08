package com.cubetiqs.security.jwt

import com.cubetiqs.security.jwt.util.JwtUtils
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

open class JwtSecurityConfigurer(private val userDetailsService: UserDetailsService) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    override fun configure(http: HttpSecurity) {
        JwtUtils.setUserDetailsService(userDetailsService)
        http.addFilterBefore(JwtTokenFilter(), BasicAuthenticationFilter::class.java)
    }
}