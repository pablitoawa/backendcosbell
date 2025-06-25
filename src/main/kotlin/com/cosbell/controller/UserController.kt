package com.cosbell.controller

import com.cosbell.entity.User
import com.cosbell.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.cosbell.dto.RegisterRequest

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody request: RegisterRequest): User {
        return userService.createUser(request)
    }

    @GetMapping("/employees")
    fun getEmployees(): List<User> {
        return userService.getEmployees()
    }
}