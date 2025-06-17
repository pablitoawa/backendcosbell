package com.cosbell.controller

import com.cosbell.dto.AppointmentDTO
import com.cosbell.entity.Appointment
import com.cosbell.service.AppointmentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalTime

@RestController
@RequestMapping("/api/citas")
class AppointmentController(private val appointmentService: AppointmentService) {
    @PostMapping
    fun create(@RequestBody request: AppointmentDTO): Appointment =
        appointmentService.createAppointment(request)

    @GetMapping("/user/{userId}")
    fun getAppointmentsByUserId(@PathVariable userId: Long): ResponseEntity<List<Appointment>> {
        val citas = appointmentService.findByUserId(userId)
        return ResponseEntity.ok(citas)
    }

    @DeleteMapping("/{id}")
    fun deleteAppointment(@PathVariable id: Long): ResponseEntity<Void> {
        appointmentService.deleteAppointment(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun getAppointmentById(@PathVariable id: Long): ResponseEntity<Appointment> {
        val cita = appointmentService.findAppointmentById(id)
        return if (cita != null) ResponseEntity.ok(cita) else ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: AppointmentDTO): ResponseEntity<Appointment> {
        return try {
            val updatedCita = appointmentService.updateAppointment(id, request)
            ResponseEntity.ok(updatedCita)
        } catch (e: Exception) {
            when (e.message) {
                "Cita no encontrada" -> ResponseEntity.notFound().build()
                "Servicio no encontrado al actualizar" -> ResponseEntity.badRequest().body(null)
                "Horario ocupado" -> ResponseEntity.badRequest().body(null)
                else -> ResponseEntity.internalServerError().body(null)
            }
        }
    }
}