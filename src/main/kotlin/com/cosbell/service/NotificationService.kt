package com.cosbell.service

import com.cosbell.entity.Appointment
import com.cosbell.repository.UserRepository
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import org.springframework.mail.SimpleMailMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

@Service
class NotificationService(
    private val mailSender: JavaMailSender,
    private val restTemplate: RestTemplate,
    private val userRepository: UserRepository,
    @Value("\${spring.mail.username}") private val fromEmail: String,
    @Value("\${whatsapp.phone-number-id}") private val whatsappPhoneNumberId: String,
    @Value("\${whatsapp.access-token}") private val whatsappAccessToken: String
) {

    private val objectMapper: ObjectMapper = jacksonObjectMapper()

    fun sendAppointmentConfirmationEmail(appointment: Appointment) {
        val user = userRepository.findById(appointment.userId)
            .orElseThrow { Exception("Usuario no encontrado para el ID: ${appointment.userId}") }

        val message = SimpleMailMessage()
        message.setFrom(fromEmail)
        message.setTo(appointment.email)
        message.setSubject("Confirmación de Cita Cosbell SPA")
        message.setText("""
            ¡Hola ${user.name}!

            Tu cita ha sido confirmada con éxito.
            Detalles de la cita:
            Servicio: ${appointment.servicio.name}
            Fecha: ${appointment.fecha}
            Hora: ${appointment.hora}
            Profesional: ${appointment.employee.name}

            ¡Esperamos verte pronto!
            Atentamente,
            Cosbell SPA
        """.trimIndent())
        mailSender.send(message)
    }

    fun sendAppointmentConfirmationWhatsApp(appointment: Appointment) {
        val user = userRepository.findById(appointment.userId)
            .orElseThrow { Exception("Usuario no encontrado para el ID: ${appointment.userId}") }

        val url = "https://graph.facebook.com/v19.0/$whatsappPhoneNumberId/messages"
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            setBearerAuth(whatsappAccessToken)
        }

        val body = mapOf(
            "messaging_product" to "whatsapp",
            "to" to "${appointment.phone}", // Asumiendo que el número de teléfono es con el código de país. Ajustar si es necesario.
            "type" to "template",
            "template" to mapOf(
                "name" to "appointment_confirmation", // Este es un nombre de plantilla de ejemplo. Debes crear una en Meta for Developers.
                "language" to mapOf("code" to "es"),
                "components" to listOf(
                    mapOf(
                        "type" to "body",
                        "parameters" to listOf(
                            mapOf("type" to "text", "text" to user.name),
                            mapOf("type" to "text", "text" to appointment.servicio.name),
                            mapOf("type" to "text", "text" to appointment.fecha.toString()),
                            mapOf("type" to "text", "text" to appointment.hora.toString()),
                            mapOf("type" to "text", "text" to appointment.employee.name)
                        )
                    )
                )
            )
        )

        val request = HttpEntity(objectMapper.writeValueAsString(body), headers)
        restTemplate.postForEntity(url, request, String::class.java)
    }
} 