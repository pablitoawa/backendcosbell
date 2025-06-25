package com.cosbell.service

import com.cosbell.dto.AppointmentDTO
import com.cosbell.entity.Appointment
import com.cosbell.repository.AppointmentRepository
import com.cosbell.repository.ServicioRepository
import com.cosbell.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AppointmentService(
    private val appointmentRepository: AppointmentRepository,
    private val servicioRepository: ServicioRepository,
    private val notificationService: NotificationService,
    private val userRepository: UserRepository
) {
    fun createAppointment(request: AppointmentDTO): Appointment {
        println("Attempting to find service with ID: ${request.serviceId}")
        val servicio = servicioRepository.findById(request.serviceId)
            .orElseThrow { 
                println("Service with ID ${request.serviceId} not found in repository.")
                Exception("Servicio no encontrado") 
            }

        val employee = userRepository.findById(request.employeeId)
            .orElseThrow { Exception("Empleado no encontrado con ID: ${request.employeeId}") }

        if (appointmentRepository.existsByServicioAndFechaAndHora(servicio, request.fecha, request.hora)) {
            throw Exception("Horario ocupado")
        }

        val appointment = Appointment(
            servicio = servicio,
            userId = request.userId,
            fecha = request.fecha,
            hora = request.hora,
            email = request.email,
            phone = request.phone,
            employee = employee
        )

        val saved = appointmentRepository.save(appointment)
        notificationService.sendAppointmentConfirmationEmail(saved)
        return saved
    }

    fun findByUserId(userId: Long): List<Appointment> {
        return appointmentRepository.findByUserId(userId)
    }

    fun deleteAppointment(id: Long) {
        appointmentRepository.deleteById(id)
    }

    fun findAppointmentById(id: Long): Appointment? {
        return appointmentRepository.findById(id).orElse(null)
    }

    fun updateAppointment(id: Long, request: AppointmentDTO): Appointment {
        val existingAppointment = appointmentRepository.findById(id)
            .orElseThrow { Exception("Cita no encontrada") }

        // Validar disponibilidad de horario antes de actualizar
        val newServicio = servicioRepository.findById(request.serviceId)
            .orElseThrow { Exception("Servicio no encontrado al actualizar") }

        val newEmployee = userRepository.findById(request.employeeId)
            .orElseThrow { Exception("Empleado no encontrado con ID: ${request.employeeId}") }

        if (appointmentRepository.existsByServicioAndFechaAndHora(newServicio, request.fecha, request.hora) &&
            !(existingAppointment.servicio.id == newServicio.id &&
              existingAppointment.fecha == request.fecha &&
              existingAppointment.hora == request.hora)) {
            throw Exception("Horario ocupado")
        }

        val updatedAppointment = existingAppointment.copy(
            servicio = newServicio,
            userId = request.userId,
            fecha = request.fecha,
            hora = request.hora,
            email = request.email,
            phone = request.phone,
            employee = newEmployee
        )
        val saved = appointmentRepository.save(updatedAppointment)

        notificationService.sendAppointmentConfirmationEmail(saved)

        return saved
    }

    fun getAllAppointments(fecha: LocalDate?, employeeId: Long?, servicioId: Long?, userId: Long?): List<Appointment> {
        return when {
            fecha != null -> appointmentRepository.findByFecha(fecha)
            employeeId != null -> appointmentRepository.findByEmployee_Id(employeeId)
            servicioId != null -> appointmentRepository.findByServicio_Id(servicioId)
            userId != null -> appointmentRepository.findByUserId(userId)
            else -> appointmentRepository.findAll()
        }
    }
}