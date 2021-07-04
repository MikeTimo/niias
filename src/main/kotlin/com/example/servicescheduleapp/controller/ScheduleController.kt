package com.example.servicescheduleapp.controller

import com.example.servicescheduleapp.service.ScheduleService
import com.example.servicescheduleapp.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/schedule")
class ScheduleController {

    @Autowired
    lateinit var scheduleService: ScheduleService

    @RequestMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    fun getTrainSchedule(
            @RequestParam(value = "trainNumber") trainNumber: Int
    ): List<Schedule>? {
        return scheduleService.getScheduleOnDayByTrain(trainNumber)
    }

    @RequestMapping("/get-list")
    @ResponseStatus(HttpStatus.OK)
    fun getSchedules(
            @RequestParam(value = "startDataTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) startDateTime: LocalDateTime,
            @RequestParam("endDataTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) endDateTime: LocalDateTime?
    ): List<Schedule> {
        return scheduleService.getSchedulesBetweenTimePoint(startDateTime, endDateTime)
    }

    @RequestMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveSchedule(@RequestParam(value = "trainNumber") trainNumber: Int, @RequestBody schedule: Schedule): String {
        scheduleService.saveSchedule(trainNumber, schedule)
        return "OK"
    }
}