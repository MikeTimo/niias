package com.example.restservice.controller

import com.example.restservice.model.Schedule
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.Month

import java.time.LocalDateTime

@RestController
class Controller {

    @GetMapping("/schedule")
    fun get(@RequestParam(value = "data") data: LocalDate): Schedule {
        val dataFrom = LocalDateTime.of(2017, Month.JULY, 9, 11, 6, 0)
        val dataTo = LocalDateTime.of(2017, Month.JULY, 9, 13, 15, 0)
        return Schedule(2, 37, 5, dataFrom, 10, dataTo)
    }

    @PostMapping("/schedule")
    @ResponseStatus(HttpStatus.OK)
    fun post(@RequestBody schedule: Schedule): String {
        return "OK"
    }
}