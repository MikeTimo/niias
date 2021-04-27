package com.example.restservice

import NiiasProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(NiiasProperties::class)
class RestServiceApplication

fun main(args: Array<String>) {
    val context =  runApplication<RestServiceApplication>(*args)
    val start = context.getBean(NiiasProperties::class.java)
    start.addFirstElement()
}