package com.cosbell.repository

import com.cosbell.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
}

/*interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?}*/