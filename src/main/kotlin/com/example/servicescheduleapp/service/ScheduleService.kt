package com.example.servicescheduleapp.service

import com.example.servicescheduleapp.model.Schedule
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.concurrent.ConcurrentHashMap

@Service
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
    fun getSchedules(startDataTime: LocalDateTime, endDataTime: LocalDateTime): List<Schedule> {
        val startScheduleTime = startDataTime
        val endScheduleTime = endDataTime ?: LocalDate.now().atTime(23, 59, 59)
        val listWithTrueSchedule: MutableList<Schedule> = ArrayList()
        for (schedule in scheduleMap) {
            if (schedule.value.departureTime.isAfter(startScheduleTime)&&schedule.value.departureTime.isBefore(endScheduleTime)) {
                listWithTrueSchedule.add(schedule.value)
            }
            if (schedule.value.arrivalTime.isAfter(startScheduleTime)&&schedule.value.arrivalTime.isBefore(endScheduleTime)) {
                listWithTrueSchedule.add(schedule.value)
            }
            if (schedule.value.arrivalTime.isBefore(startScheduleTime)&&schedule.value.departureTime.isAfter(endScheduleTime)) {
                listWithTrueSchedule.add(schedule.value)
            }
        }
        return listWithTrueSchedule
    }

    @Synchronized
    fun saveSchedule(schedule: Schedule) {
        scheduleMap[schedule.trainNumber] = schedule
    }
}
