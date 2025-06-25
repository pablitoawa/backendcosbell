package com.cosbell.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/config")
class ConfigController {

    @GetMapping("/check")
    fun getFDate(): Map<String, String> {
        // La fecha para la activación del flashbang
        val checK = "2025-06-30T12:00:00"
        return mapOf("checK" to checK)
    }
} 