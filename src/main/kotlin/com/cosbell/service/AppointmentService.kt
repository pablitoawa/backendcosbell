package com.cosbell.service

import com.cosbell.dto.AppointmentDTO
import com.cosbell.entity.Appointment
import com.cosbell.repository.AppointmentRepository
import com.cosbell.repository.ServicioRepository
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class AppointmentService(
    private val appointmentRepository: AppointmentRepository,
    private val servicioRepository: ServicioRepository,
    private val mailSender: JavaMailSender
) {
    fun createAppointment(request: AppointmentDTO): Appointment {
        println("Attempting to find service with ID: ${request.serviceId}")
        val servicio = servicioRepository.findById(request.serviceId)
            .orElseThrow { 
                println("Service with ID ${request.serviceId} not found in repository.")
                Exception("Servicio no encontrado") 
            }

        if (appointmentRepository.existsByServicioAndFechaAndHora(servicio, request.fecha, request.hora)) {
            throw Exception("Horario ocupado")
        }

        val appointment = Appointment(
            servicio = servicio,
            userId = request.userId,
            fecha = request.fecha,
            hora = request.hora,
            email = request.email
        )

        val saved = appointmentRepository.save(appointment)
        enviarConfirmacion(request.email, "Tu cita fue agendada para el ${request.fecha} a las ${request.hora}.")
        return saved
    }

    private fun enviarConfirmacion(email: String, mensaje: String) {
        val mail = SimpleMailMessage()
        mail.setTo(email)
        mail.setSubject("Confirmación de cita")
        mail.setText(mensaje)
        mailSender.send(mail)
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
            email = request.email
        )
        val saved = appointmentRepository.save(updatedAppointment)

        // Enviar confirmación de modificación
        val subject = "Confirmación de Modificación de Cita"
        val message = "Tu cita ha sido modificada. Nuevos detalles: Servicio: ${updatedAppointment.servicio.name}, Fecha: ${updatedAppointment.fecha}, Hora: ${updatedAppointment.hora}."
        enviarConfirmacion(updatedAppointment.email, message)

        return saved
    }
}