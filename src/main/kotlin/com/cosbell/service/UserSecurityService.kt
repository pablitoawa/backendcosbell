package com.cosbell.service

/*import com.cosbell.config.JwtUtil
import com.cosbell.dto.LoginRequest
import com.cosbell.dto.TokenDto
import com.cosbell.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserSecurityService : UserDetailsService {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Usuario no encontrado")

        // Sin roles, autoridades vacías
        val authorityList = emptyList<SimpleGrantedAuthority>()

        return org.springframework.security.core.userdetails.User.builder()
            .username(userEntity.username!!)
            .password(userEntity.password!!)
            .authorities(authorityList)
            .accountLocked(userEntity.locked ?: false)
            .disabled(userEntity.disabled ?: false)
            .build()
    }

    fun login(loginDto: LoginRequest): TokenDto {
        val authentication = authenticate(loginDto.username!!, loginDto.password!!)
        SecurityContextHolder.getContext().authentication = authentication
        val accessToken = jwtUtil.create(authentication)
        return TokenDto().apply {
            jwt = accessToken
        }
    }

    fun authenticate(username: String, password: String): Authentication {
        val userDetails = loadUserByUsername(username)

        if (!passwordEncoder.matches(password, userDetails.password)) {
            throw UsernameNotFoundException("Credenciales incorrectas")
        }

        return UsernamePasswordAuthenticationToken(username, null, userDetails.authorities)
    }
}*/

/*package com.cosbell.spa.service

import com.cosbell.spa.config.JwtUtil
import com.cosbell.spa.dto.LoginRequest
import com.cosbell.spa.dto.TokenDto
import com.cosbell.spa.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
/*import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserSecurityService : UserDetailsService {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val userEntity = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("Usuario no encontrado")

        val authorityList = emptyList<SimpleGrantedAuthority>()

        return org.springframework.security.core.userdetails.User.builder()
            .username(userEntity.email)
            .password(userEntity.password)
            .authorities(authorityList)
            .build()
    }

    fun login(loginDto: LoginRequest): TokenDto {
        val authentication = authenticate(loginDto.email, loginDto.password)
        SecurityContextHolder.getContext().authentication = authentication
        val accessToken = jwtUtil.generateToken(authentication.name)
        return TokenDto().apply {
            jwt = accessToken
        }
    }

    fun authenticate(email: String, password: String): Authentication {
        val userDetails = loadUserByUsername(email)

        if (!passwordEncoder.matches(password, userDetails.password)) {
            throw UsernameNotFoundException("Credenciales incorrectas")
        }

        return UsernamePasswordAuthenticationToken(email, null, userDetails.authorities)
    }
}*/