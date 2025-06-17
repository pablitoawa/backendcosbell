package com.cosbell.entity

import jakarta.persistence.*


@Entity
@Table(name = "service")
data class Servicio(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val duration: Int,   //en minutos

    @Column(nullable = false)
    val price: Double
)