package com.cosbell.repository

import com.cosbell.entity.Horario
import org.springframework.data.jpa.repository.JpaRepository

interface HorarioRepository : JpaRepository<Horario, Long> {
    fun findByDia(dia: String): List<Horario>
}
