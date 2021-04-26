package com.example.restservice.controller

import com.example.restservice.service.ScheduleService
import com.example.restservice.model.Schedule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
class Controller {

    @Autowired
    lateinit var scheduleService: ScheduleService

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