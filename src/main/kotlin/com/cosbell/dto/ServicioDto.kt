package com.cosbell.dto

data class ServicioDTO(
    val id: Long? = null,
    val name: String,
    val duration: Int,
    val price: Double
)