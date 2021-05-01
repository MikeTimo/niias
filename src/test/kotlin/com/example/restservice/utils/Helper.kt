package com.example.restservice.utils

import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap


class Helper() {
    fun getSchedule(trainNumber: Int) : ScheduleTest? {
        var scheduleMap: MutableMap<Int, ScheduleTest> = ConcurrentHashMap()

        val departureTime = LocalDateTime.now()
        val arrivalTime = departureTime.plusHours(1).plusMinutes(30)

        val scheduleTest = ScheduleTest(
            1, 4,
            1, departureTime, 5, arrivalTime
        )

        scheduleMap[1] = scheduleTest

        return scheduleMap[trainNumber]
    }
}