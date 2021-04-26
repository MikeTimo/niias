package com.example.restservice.service

import com.example.restservice.model.Schedule
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.concurrent.ConcurrentHashMap

@Component
class ScheduleService {
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
