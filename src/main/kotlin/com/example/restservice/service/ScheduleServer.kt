package com.example.restservice.service

import com.example.restservice.model.Schedule
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime
import java.util.concurrent.ConcurrentHashMap

@Service
class ScheduleServer {
    var scheduleMap: MutableMap<Int, Schedule> = ConcurrentHashMap()

    @Synchronized
    fun getSchedule(trainNumber: Int, data: LocalDate): Schedule? {
        return scheduleMap[trainNumber]
    }

    @Synchronized
    fun saveSchedule(schedule: Schedule) {
        scheduleMap[schedule.trainNumber] = schedule
    }
}
