package com.cosbell.service

import com.cosbell.dto.AuthResponse
import com.cosbell.dto.LoginRequest
import com.cosbell.dto.RegisterRequest
import com.cosbell.entity.User
import com.cosbell.repository.RoleRepository
import com.cosbell.repository.UserRepository
import com.cosbell.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/*@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {
    fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.existsByEmail(request.email)) {
            return AuthResponse(message = "El correo ya está registrado")
        }
        val user = User(
            name = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            roles = Role.valueOf(request.role.uppercase())
        )
        userRepository.save(user)
        val token = jwtService.generateToken(user)
        return AuthResponse(token = token, role = user.roles.name, message = "Usuario registrado correctamente")
    }

    fun login(request: LoginRequest): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.password)
        )
        val user = userRepository.findByEmail(request.email)
            ?: return AuthResponse(message = "Credenciales incorrectas")
        val token = jwtService.generateToken(user)
        return AuthResponse(token = token, role = user.roles.name, message = "Login exitoso")
    }
}*/
@Service
class AuthService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {
    fun register(request: RegisterRequest): AuthResponse {
        println("Register request received for email: ${request.email} with role: ${request.role}")
        if (userRepository.existsByEmail(request.email)) {
            println("Registration failed: Email already registered.")
            return AuthResponse(message = "El correo ya está registrado")
        }

        val role = roleRepository.findByName(request.role.uppercase())
            ?: run {
                println("Registration failed: Role not found for role name: ${request.role.uppercase()}")
                return AuthResponse(message = "Rol no encontrado")
            }
        println("Role found from DB: ${role.name}")

        val user = User(
            name = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            roles = listOf(role)
        )
        userRepository.save(user)
        val token = jwtService.generateToken(user)
        return AuthResponse(token = token, email = user.email, role = role.name, message = "Registro exitoso", userId = user.id)
    }

    fun login(request: LoginRequest): AuthResponse {
        println("Login request received for email: ${request.email}")
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(request.email, request.password)
            )
        } catch (e: Exception) {
            println("Login failed: Incorrect credentials for email: ${request.email}")
            return AuthResponse(message = "Credenciales incorrectas")
        }

        val user = userRepository.findByEmail(request.email)
            ?: run {
                println("Login failed: User not found after authentication for email: ${request.email}")
                return AuthResponse(message = "Credenciales incorrectas")
            }
        val token = jwtService.generateToken(user)
        val roleName = user.roles.firstOrNull()?.name?.replace("ROLE_", "")?.uppercase()
        println("Login successful for email: ${user.email}. Role sent to frontend: ${roleName}")
        return AuthResponse(token = token, email = user.email, role = roleName, message = "Inicio de sesión exitoso", userId = user.id)
    }
}