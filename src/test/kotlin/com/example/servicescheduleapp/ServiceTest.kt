package com.example.servicescheduleapp

import com.example.servicescheduleapp.service.ScheduleService
import org.junit.Assert.*
import com.example.servicescheduleapp.model.Schedule
import org.junit.Before
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

    @Before
    fun preparation() {
        // Время отправки и прибытия находится в интревале
        val departureTime = LocalDateTime.of(2020, 2, 3, 10, 0, 0)
        val arrivalTime = LocalDateTime.of(2020, 2, 3, 11, 0, 0)
        val schedule = Schedule(1, 5, 5, 5,
            101, 65, 1, 1,
            departureTime, departureTime.minusHours(1).withNano(0), 5, 5,
            arrivalTime, arrivalTime.plusMinutes(30).withNano(0), "54")

        // Только время отправки состава попадает в интервал
        val departureTime2 = LocalDateTime.of(2020, 2, 3, 10, 0, 0)
        val arrivalTime2 = LocalDateTime.of(2020, 2, 3, 14, 0, 0)
        val schedule2 = Schedule(4, 5, 9, 10,
            101, 65, 1, 1,
            departureTime2, departureTime2.minusHours(1).withNano(0), 5, 5,
            arrivalTime2, arrivalTime2.plusMinutes(30).withNano(0), "54")

        // Только время прибытия попадает в интервал
        val departureTime3 = LocalDateTime.of(2020, 2, 3, 7, 0, 0)
        val arrivalTime3 = LocalDateTime.of(2020, 2, 3, 11, 0, 0)
        val schedule3 = Schedule(4, 5, 9, 10,
            101, 65, 1, 1,
            departureTime3, departureTime3.minusHours(1).withNano(0), 5, 5,
            arrivalTime3, arrivalTime3.plusMinutes(30).withNano(0), "54")

        // Время отправки раньше страта интервала, время прибытия позже конца интервала
        val departureTime4 = LocalDateTime.of(2020, 2, 3, 8, 0, 0)
        val arrivalTime4 = LocalDateTime.of(2020, 2, 3, 15, 0, 0)
        val schedule4 = Schedule(4, 5, 9, 10,
            101, 65, 1, 1,
            departureTime4, departureTime4.minusHours(1).withNano(0), 5, 5,
            arrivalTime4, arrivalTime4.plusMinutes(30).withNano(0), "54")

        // Время отправки и прибытия не попадает в интервал
        val departureTime5 = LocalDateTime.of(2020, 2, 3, 5, 0, 0)
        val arrivalTime5 = LocalDateTime.of(2020, 2, 3, 6, 0, 0)
        val schedule5 = Schedule(4, 5, 9, 10,
            101, 65, 1, 1,
            departureTime5, departureTime5.minusHours(1).withNano(0), 5, 5,
            arrivalTime5, arrivalTime5.plusMinutes(30).withNano(0), "54")

        val list: MutableList<Schedule> = mutableListOf(schedule, schedule2, schedule3, schedule4, schedule5)
        scheduleService.scheduleMap[schedule.trainNumber] = list
    }

    @Test
    fun getTrainSchedule() {
        val departureTime = LocalDateTime.of(2020, 2, 3, 10, 0, 0)
        val arrivalTime = LocalDateTime.of(2020, 2, 3, 11, 0, 0)
        val schedule = Schedule(1, 5, 5, 5,
            101, 65, 1, 1,
            departureTime, departureTime.minusHours(1).withNano(0), 5, 5,
            arrivalTime, arrivalTime.plusMinutes(30).withNano(0), "54")

        val departureTime2 = LocalDateTime.of(2020, 2, 3, 10, 0, 0)
        val arrivalTime2 = LocalDateTime.of(2020, 2, 3, 14, 0, 0)
        val schedule2 = Schedule(4, 5, 9, 10,
            101, 65, 1, 1,
            departureTime2, departureTime2.minusHours(1).withNano(0), 5, 5,
            arrivalTime2, arrivalTime2.plusMinutes(30).withNano(0), "54")

        val departureTime3 = LocalDateTime.of(2020, 2, 3, 7, 0, 0)
        val arrivalTime3 = LocalDateTime.of(2020, 2, 3, 11, 0, 0)
        val schedule3 = Schedule(4, 5, 9, 10,
            101, 65, 1, 1,
            departureTime3, departureTime3.minusHours(1).withNano(0), 5, 5,
            arrivalTime3, arrivalTime3.plusMinutes(30).withNano(0), "54")

        val departureTime4 = LocalDateTime.of(2020, 2, 3, 8, 0, 0)
        val arrivalTime4 = LocalDateTime.of(2020, 2, 3, 15, 0, 0)
        val schedule4 = Schedule(4, 5, 9, 10,
            101, 65, 1, 1,
            departureTime4, departureTime4.minusHours(1).withNano(0), 5, 5,
            arrivalTime4, arrivalTime4.plusMinutes(30).withNano(0), "54")

        val departureTime5 = LocalDateTime.of(2020, 2, 3, 5, 0, 0)
        val arrivalTime5 = LocalDateTime.of(2020, 2, 3, 6, 0, 0)
        val schedule5 = Schedule(4, 5, 9, 10,
            101, 65, 1, 1,
            departureTime5, departureTime5.minusHours(1).withNano(0), 5, 5,
            arrivalTime5, arrivalTime5.plusMinutes(30).withNano(0), "54")

        val expectedList: MutableList<Schedule> = mutableListOf(schedule, schedule2, schedule3, schedule4, schedule5)
        val numberGetTrain = 5
        val actualSchedules = scheduleService.scheduleMap[numberGetTrain]

        assertNotNull(actualSchedules)
        assertEquals(expectedList, actualSchedules)
    }

    @Test
    fun getScheduleListWhenScheduleInTimeIntervals() {
        val startTimeInterval = LocalDateTime.of(2020, 2, 3, 9, 0, 0)
        val endTimeInterval = LocalDateTime.of(2020, 2, 3, 12, 0, 0)
        val list: List<Schedule> = scheduleService.getSchedules(startTimeInterval, endTimeInterval)

        val expectedListSize = 4
        val actualListSize = list.size

        assertEquals(expectedListSize, actualListSize)
    }

    @Test
    fun getScheduleListWhenScheduleNoInTimeIntervals() {
        val startTimeInterval = LocalDateTime.of(2020, 2, 3, 9, 0, 0)
        val endTimeInterval = LocalDateTime.of(2020, 2, 3, 12, 0, 0)
        val list: List<Schedule> = scheduleService.getSchedules(startTimeInterval, endTimeInterval)

        val expectedListSize = 1
        val actualListSize = 5 - list.size

        assertEquals(expectedListSize, actualListSize)
    }

    @Test
    fun saveSchedule() {
        val expectedListSize = 5
        val scheduleList = scheduleService.scheduleMap[5]
        val actualListSize = scheduleList!!.size

        assertEquals(expectedListSize, actualListSize)
    }
}