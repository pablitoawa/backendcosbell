package com.cosbell.dto

import java.time.LocalDate
import java.time.LocalTime
import com.fasterxml.jackson.annotation.JsonProperty

data class AppointmentDTO(
    @JsonProperty("servicioId")
    val serviceId: Long,
    val userId: Long,
    val fecha: LocalDate,
    val hora: LocalTime,
    val email: String
)
