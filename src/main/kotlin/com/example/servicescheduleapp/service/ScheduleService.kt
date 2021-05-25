package com.example.servicescheduleapp.service

import com.example.servicescheduleapp.model.Schedule
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Service
class ScheduleService {
    var scheduleMap: MutableMap<Int, MutableList<Schedule>> = ConcurrentHashMap()

    @Synchronized
    fun getSchedule(trainNumber: Int): List<Schedule>? {
        val scheduleList: List<Schedule> = return if (scheduleMap.containsKey(trainNumber)) {
            scheduleMap[trainNumber]!!
        } else {
            null
        }
    }

    @Synchronized
    fun getSchedules(startDataTime: LocalDateTime, endDataTime: LocalDateTime): List<Schedule> {
        val endScheduleTime = endDataTime ?: LocalDate.now().atTime(23, 59, 59)
        val listWithTrueSchedule: MutableList<Schedule> = ArrayList()
        for ((key, value) in scheduleMap) {
            for (schedule in value) {
                if (schedule.departureTime.isAfter(startDataTime) && schedule.departureTime.isBefore(endScheduleTime)) {
                    listWithTrueSchedule.add(schedule)
                }
                if (schedule.arrivalTime.isAfter(startDataTime) && schedule.arrivalTime.isBefore(endScheduleTime)) {
                    listWithTrueSchedule.add(schedule)
                }
                if (schedule.arrivalTime.isBefore(startDataTime) && schedule.departureTime.isAfter(endScheduleTime)) {
                    listWithTrueSchedule.add(schedule)
                }
            }
        }
        return listWithTrueSchedule
    }

    @Synchronized
    fun saveSchedule(schedule: Schedule) {
        var scheduleList: MutableList<Schedule> = ArrayList()
        if (scheduleMap.containsKey(schedule.trainNumber)) {
            scheduleList = scheduleMap[schedule.trainNumber]!!
            scheduleList.add(schedule)
            scheduleMap[schedule.trainNumber] = scheduleList
        } else {
            scheduleList.add(schedule)
            scheduleMap[schedule.trainNumber] = scheduleList
        }
    }
}
