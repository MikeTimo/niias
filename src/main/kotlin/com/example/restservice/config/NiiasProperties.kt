package com.example.restservice.config

import com.example.restservice.service.ScheduleServer
import com.example.restservice.model.Schedule
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@ConfigurationProperties("app.schedule")
class NiiasProperties {
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







}