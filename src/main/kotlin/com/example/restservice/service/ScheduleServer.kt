package com.example.restservice.service

import com.example.restservice.model.Schedule
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.ConcurrentHashMap

@Service
class ScheduleServer {
    /*
    ключ - trainNumber
    значение - Schedule
     */
    var scheduleMap: MutableMap<LocalDate, MutableMap<Int, Schedule>> = ConcurrentHashMap()

    fun getSchedule(trainNumber: Int, data: LocalDate): Schedule? {
        val timeNow = LocalTime.now()
        val departureTime: LocalDateTime = data.atTime(timeNow)
        return if (scheduleMap.containsKey(data)) {
            val map: MutableMap<Int, Schedule>? = scheduleMap[data]
            if (map!!.containsKey(trainNumber)) {
                map!![trainNumber]!!
            } else {
                null
            }
        } else {
            null
        }
    }

    fun saveSchedule(schedule: Schedule) {
        /*
        получаю дату и номер поезда из расписания
         */
        val data = schedule.departureTime.toLocalDate()
        val trainNumber = schedule.trainNumber

        /*
        если такая дата есть, то проверяю номер поезда
        если такой даты нет, то вношу новую данные в мапу
         */
        if (scheduleMap.containsKey(data)) {
            val map: MutableMap<Int, Schedule>? = scheduleMap[data]
            map!![trainNumber] = schedule
        } else {
            var map: MutableMap<Int, Schedule> = ConcurrentHashMap()
            map[trainNumber] = schedule
            scheduleMap[data] = map
        }
    }
}
