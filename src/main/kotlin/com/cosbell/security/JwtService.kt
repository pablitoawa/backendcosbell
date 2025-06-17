package com.cosbell.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.cosbell.entity.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService {
    private val secretKey = "mi_clave_secreta_segura"

    fun generateToken(user: User): String {
        val algorithm = Algorithm.HMAC256(secretKey)
        return JWT.create()
            .withSubject(user.email)
            .withClaim("roles", user.roles.mapNotNull { it.name })
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
            .sign(algorithm)
    }

    fun extractUsername(token: String): String? {
        return try {
            val decodedJWT = validateToken(token)
            decodedJWT.subject
        } catch (e: Exception) {
            null
        }
    }

    fun validateToken(token: String): DecodedJWT {
        val algorithm = Algorithm.HMAC256(secretKey)
        return JWT.require(algorithm).build().verify(token)
    }
}