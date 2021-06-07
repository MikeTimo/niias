package com.example.servicescheduleapp.service

import com.example.servicescheduleapp.model.Schedule
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Service
class ScheduleService {
    var scheduleMap: MutableMap<Int, MutableList<Schedule>> = ConcurrentHashMap()

    @Synchronized
    fun getScheduleOnDayByTrain(trainNumber: Int): List<Schedule>? {
        var scheduleList: MutableList<Schedule> = ArrayList()
        if (trainNumber != null) {
            if (scheduleMap.containsKey(trainNumber) && scheduleMap[trainNumber] != null) {
                scheduleList = scheduleMap[trainNumber]!!
            }
        }
        return scheduleList
    }

    @Synchronized
    fun getSchedulesBetweenTimePoint(startDataTime: LocalDateTime, endDataTime: LocalDateTime?): List<Schedule> {
        val endScheduleTime: LocalDateTime
        if (endDataTime == null) {
            val dataRequest = startDataTime.toLocalDate()
            endScheduleTime = dataRequest.atTime(23, 59, 59)
        } else {
            endScheduleTime = endDataTime
        }
        val listWithTrueSchedule: MutableList<Schedule> = ArrayList()
        for ((key, value) in scheduleMap) {
            for (schedule in value) {
                if (schedule.departureTime.isAfter(startDataTime) && schedule.arrivalTime.isBefore(endScheduleTime)) {
                    listWithTrueSchedule.add(schedule)
                } else if (schedule.departureTime.isBefore(startDataTime) && schedule.arrivalTime.isAfter(startDataTime) && schedule.arrivalTime.isBefore(endScheduleTime)) {
                    listWithTrueSchedule.add(schedule)
                } else if (schedule.departureTime.isAfter(startDataTime) && schedule.departureTime.isBefore(endScheduleTime) && schedule.arrivalTime.isAfter(endScheduleTime)) {
                    listWithTrueSchedule.add(schedule)
                } else if (schedule.departureTime.isBefore(startDataTime) && schedule.arrivalTime.isAfter(endScheduleTime)) {
                    listWithTrueSchedule.add(schedule)
                }
            }
        }
        return listWithTrueSchedule
    }

    @Synchronized
    fun saveSchedule(trainNumber: Int, schedule: Schedule) {
        var scheduleList: MutableList<Schedule> = ArrayList()
        if (scheduleMap.containsKey(trainNumber)) {
            if (scheduleMap.containsKey(trainNumber) != null) {
                scheduleList = scheduleMap[trainNumber]!!
                scheduleList.add(schedule)
                scheduleMap[trainNumber] = scheduleList
            }
        } else {
            scheduleList.add(schedule)
            scheduleMap[trainNumber] = scheduleList
        }
    }
}
