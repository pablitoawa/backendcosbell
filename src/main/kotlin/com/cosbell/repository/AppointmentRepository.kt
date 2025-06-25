package com.cosbell.repository

import com.cosbell.entity.Appointment
import com.cosbell.entity.Servicio
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.time.LocalTime

/*interface AppointmentRepository : JpaRepository<Appointment, Long> {
    fun existsByServicioIdAndFechaAndHora(servicioId: Long, fecha: LocalDate, hora: LocalTime): Boolean
    fun findByEmail(email: String): List<Appointment>*/


interface AppointmentRepository : JpaRepository<Appointment, Long> {
    fun existsByServicioAndFechaAndHora(servicio: Servicio, fecha: LocalDate, hora: LocalTime): Boolean
    fun findByEmail(email: String): List<Appointment>
    fun findByFecha(fecha: LocalDate): List<Appointment>
    fun findByUserId(userId: Long): List<Appointment>
    fun findByEmployee_Id(employeeId: Long): List<Appointment>
    fun findByServicio_Id(servicioId: Long): List<Appointment>
}