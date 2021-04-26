package com.example.restservice

import NiiasProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RestServiceApplication

fun main(args: Array<String>) {
    runApplication<RestServiceApplication>(*args)
//    val context =  runApplication<RestServiceApplication>(*args)
//    val start = context.getBean(NiiasProperties::class.java)
//    start.addFirstElement()
}