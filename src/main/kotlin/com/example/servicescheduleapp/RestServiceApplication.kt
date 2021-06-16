package com.example.servicescheduleapp

import com.example.servicescheduleapp.config.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(BasicProperties::class, DriversProperties::class, StationsProperties::class, RollingStockProperties::class)
class RestServiceApplication

fun main(args: Array<String>) {
    runApplication<RestServiceApplication>(*args)
}