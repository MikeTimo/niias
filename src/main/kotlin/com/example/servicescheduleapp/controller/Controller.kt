package com.example.servicescheduleapp.controller

import com.example.servicescheduleapp.service.ScheduleService
import com.example.servicescheduleapp.model.Schedule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
class Controller {

    @Autowired
    lateinit var scheduleService: ScheduleService

    @GetMapping("/schedule")
    fun getSchedule(
        @RequestParam(value = "data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) data: LocalDate,
        @RequestParam(value = "trainNumber") trainNumber: Int
    ): Schedule? {
        return scheduleService.getSchedule(trainNumber, data)
    }

    @GetMapping("/schedule/list")
    fun getSchedules(
        @RequestParam(value = "startDataTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) startDateTime: LocalDateTime,
        @RequestParam(
            "endDataTime",
            required = false
        ) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) endDateTime: LocalDateTime
    ): List<Schedule> {
        return scheduleService.getSchedules(startDateTime, endDateTime)
    }

    @PostMapping("/schedule")
    @ResponseStatus(HttpStatus.OK)
    fun post(@RequestBody schedule: Schedule): String {
        scheduleService.saveSchedule(schedule)
        return "OK"
    }
}