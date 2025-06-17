package com.cosbell.controller

import com.cosbell.entity.Servicio
import com.cosbell.service.ServicioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/servicio")
class ServicioController(private val servicioService: ServicioService) {

    @GetMapping
    fun getAll(): List<Servicio> = servicioService.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Servicio> {
        val servicio = servicioService.findById(id)
        return if (servicio != null) ResponseEntity.ok(servicio) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun create(@RequestBody servicio: Servicio): Servicio = servicioService.save(servicio)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody updated: Servicio): ResponseEntity<Servicio> {
        val servicio = servicioService.findById(id)
        return if (servicio != null) {
            val actualizado = servicioService.save(updated.copy(id = id))
            ResponseEntity.ok(actualizado)
        } else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        return if (servicioService.findById(id) != null) {
            servicioService.deleteById(id)
            ResponseEntity.noContent().build()
        } else ResponseEntity.notFound().build()
    }
}