package com.example.restservice.config

import com.example.restservice.model.Schedule
import com.example.restservice.service.ScheduleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset.UTC

@Component
@ConfigurationProperties(prefix = "app.schedule")
@Validated
class NiiasProperties {

     @Autowired
     lateinit var scheduleService: ScheduleService

     var trainNumber: Int
     var driverNumber: Int
     var departureStation: Int
     var departureTime: LocalDateTime
     var arrivalStation: Int
     var arrivalTime: LocalDateTime

     val schedule = Schedule(
          trainNumber, driverNumber,
          departureStation, departureTime!!, arrivalStation, arrivalTime!!
     )

     fun addFirstElement() {
          scheduleService.scheduleMap[trainNumber.toInt()] = schedule
     }
}