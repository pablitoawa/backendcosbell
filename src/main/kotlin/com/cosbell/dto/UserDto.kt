package com.cosbell.dto

data class UserDTO(
    val id: Long? = null,
    val name: String,
    val email: String,
    val roleId: Long
)