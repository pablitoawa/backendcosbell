package com.cosbell

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CosbellSpaApplication

fun main(args: Array<String>) {
	runApplication<CosbellSpaApplication>(*args)
}
