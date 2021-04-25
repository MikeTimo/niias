package com.example.restservice.controller

import com.example.restservice.model.Schedule
import com.example.restservice.service.ScheduleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
class Controller {

    private lateinit var scheduleService: ScheduleService

    @GetMapping("/schedule")
    fun get(
        @RequestParam(value = "data") data: LocalDate,
        @RequestParam(value = "trainNumber") trainNumber: Int
    ): Schedule? {
        return scheduleService.getSchedule(trainNumber, data)
    }

    @PostMapping("/schedule")
    @ResponseStatus(HttpStatus.OK)
    fun post(@RequestBody schedule: Schedule): String {
        scheduleService.saveSchedule(schedule)
        return "OK"
    }
}