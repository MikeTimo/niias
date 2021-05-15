package com.example.servicescheduleapp

import com.example.servicescheduleapp.service.ScheduleService
import org.junit.Assert.*
import com.example.servicescheduleapp.model.Schedule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDateTime

@RunWith(SpringRunner::class)
@SpringBootTest
class ServiceTest {

    @Autowired
    lateinit var scheduleService: ScheduleService

    @Test
    fun getSchedule() {
        val departureTime = LocalDateTime.now()
        val arrivalTime = departureTime.plusHours(1).plusMinutes(30)
        val schedule = Schedule(1, 5, 5, 5,
            101, 65, 1, 1,
            departureTime, departureTime.minusHours(1), 5, 5,
            arrivalTime, arrivalTime.plusMinutes(30), "54")

        scheduleService.scheduleMap[schedule.trainNumber] = schedule

        val numberGetTrain = 1

        val getSchedule = scheduleService.scheduleMap[numberGetTrain]

        assertNotNull(getSchedule)

        assertEquals(schedule, getSchedule)
    }

    @Test
    fun saveSchedule() {
        val departureTime = LocalDateTime.now()
        val arrivalTime = departureTime.plusHours(1).plusMinutes(30)
        val schedule = Schedule(1, 5, 5, 5,
            101, 65, 1, 1,
            departureTime, departureTime.minusHours(1), 5, 5,
            arrivalTime, arrivalTime.plusMinutes(30), "54")

        scheduleService.scheduleMap[schedule.trainNumber] = schedule

        val expectedListSize = 1
        val actualListSize = scheduleService.scheduleMap.size

        assertEquals(expectedListSize, actualListSize)
    }
}