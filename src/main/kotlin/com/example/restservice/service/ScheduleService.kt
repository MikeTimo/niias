package com.example.restservice.service

import com.example.restservice.model.Schedule
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.concurrent.ConcurrentHashMap

@Component
class ScheduleService {
    var scheduleMap: MutableMap<Int, Schedule> = ConcurrentHashMap()

    @Synchronized
    fun getSchedule(trainNumber: Int, data: LocalDate): Schedule? {
        val dataTimeNow = data.atTime(LocalTime.now())
        val zone = dataTimeNow.atZone(ZoneId.systemDefault())
        val dataTime = zone.toLocalDateTime()
        var schedule = scheduleMap[trainNumber]
        if (schedule?.departureTime != dataTime) {
            schedule?.departureTime = dataTime
            schedule?.arrivalTime = dataTime.plusHours(1).plusMinutes(30)
            scheduleMap[trainNumber] = schedule!!
        }
        return schedule
    }

    @Synchronized
    fun saveSchedule(schedule: Schedule) {
        scheduleMap[schedule.trainNumber] = schedule
    }
}
