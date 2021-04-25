package com.example.restservice.config

import com.example.restservice.model.Schedule
import com.example.restservice.service.ScheduleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime

@Component
@ConfigurationProperties("app.schedule")
@Validated
class NiiasProperties {

     @Autowired
     lateinit var scheduleService: ScheduleService

     lateinit var trainNumber: String
     lateinit var driverNumber: String
     lateinit var departureStation: String
     lateinit var departureTime: LocalDateTime
     lateinit var arrivalStation: String
     lateinit var arrivalTime: LocalDateTime

     val schedule = Schedule(
          trainNumber.toInt(), driverNumber.toInt(),
          departureStation.toInt(), departureTime, arrivalStation.toInt(), arrivalTime
     )

     fun addFirstElement() {
          scheduleService.scheduleMap[trainNumber.toInt()] = schedule
     }







}