package com.example.restservice

import com.example.restservice.config.NiiasProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(NiiasProperties::class)
class RestServiceApplication

fun main(args: Array<String>) {
    runApplication<RestServiceApplication>(*args)
}
