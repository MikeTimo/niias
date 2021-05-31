package com.example.servicescheduleapp

import NiiasProperties
import com.example.servicescheduleapp.config.BasicConfig
import com.example.servicescheduleapp.config.StationScheduleConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(BasicConfig::class, StationScheduleConfig::class)
class RestServiceApplication

fun main(args: Array<String>) {
    val context =  runApplication<RestServiceApplication>(*args)
    val basicConfig = context.getBean("basicConfigBean", BasicConfig::class.java)
    basicConfig.addDriversInList()
    basicConfig.addAllSchedulesInList()
}