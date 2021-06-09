package com.example.servicescheduleapp

import com.example.servicescheduleapp.model.Schedule
import com.example.servicescheduleapp.service.ScheduleService

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.time.LocalTime

@SpringBootTest
class ScheduleServiceTest {

    @Autowired
    lateinit var scheduleService: ScheduleService

    @BeforeEach
    fun clearScheduleMap() {
        scheduleService.scheduleMap.clear()
    }


    @Test
    fun getScheduleOnDayByTrain() {
        val schedule1 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 10, 16, 30, 0), LocalDateTime.of(2020, 10, 10, 16, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 10, 17, 0, 0), LocalDateTime.of(2020, 10, 10, 16, 30, 0),
                "645В")
        val schedule2 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 10, 16, 30, 0), LocalDateTime.of(2020, 10, 10, 16, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 10, 17, 0, 0), LocalDateTime.of(2020, 10, 10, 16, 30, 0),
                "645В")

        val scheduleList: MutableList<Schedule> = arrayListOf(schedule1, schedule2)
        val trainNumber = 113
        scheduleService.scheduleMap[trainNumber] = scheduleList

        val expectedScheduleList = scheduleService.getScheduleOnDayByTrain(trainNumber)
        assertNotNull(expectedScheduleList)

        val actualScheduleListSize = expectedScheduleList?.size
        val expectedScheduleListSize = 2
        assertEquals(expectedScheduleListSize, actualScheduleListSize)
    }

    @Test
    fun getScheduleOnDayByTrainWhenNumberIsWrong() {
        val schedule1 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 10, 16, 30, 0), LocalDateTime.of(2020, 10, 10, 16, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 10, 17, 0, 0), LocalDateTime.of(2020, 10, 10, 16, 30, 0),
                "645В")

        val scheduleList: MutableList<Schedule> = arrayListOf(schedule1)
        val trainNumber = 113
        scheduleService.scheduleMap[trainNumber] = scheduleList

        val falseTrainNumber = 114

        val expectedScheduleList = scheduleService.getScheduleOnDayByTrain(falseTrainNumber)
        assertNotNull(expectedScheduleList)

        val actualScheduleListSize = expectedScheduleList?.size
        val expectedScheduleListSize = 0
        assertEquals(expectedScheduleListSize, actualScheduleListSize)
    }

    @Test
    fun getSchedulesBetweenTimePoint() {
        val schedule1 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 10, 11, 30, 0), LocalDateTime.of(2020, 10, 10, 11, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 10, 16, 0, 0), LocalDateTime.of(2020, 10, 10, 20, 30, 0),
                "645В")
        val schedule2 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 10, 9, 30, 0), LocalDateTime.of(2020, 10, 10, 16, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 10, 16, 0, 0), LocalDateTime.of(2020, 10, 10, 19, 30, 0),
                "645В")
        val schedule3 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 10, 16, 30, 0), LocalDateTime.of(2020, 10, 10, 9, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 10, 20, 0, 0), LocalDateTime.of(2020, 10, 10, 21, 30, 0),
                "645В")

        val scheduleList = arrayListOf<Schedule>(schedule1, schedule2, schedule3)
        val trainNumber = 113

        scheduleService.scheduleMap[trainNumber] = scheduleList

        val startDataTime = LocalDateTime.of(2020, 10, 10, 10, 0, 0)
        val endDataTime = LocalDateTime.of(2020, 10, 10, 17, 0, 0)

        val expectedScheduleList = scheduleService.getSchedulesBetweenTimePoint(startDataTime, endDataTime)
        assertNotNull(expectedScheduleList)

        val actualScheduleListSize = expectedScheduleList?.size
        val expectedScheduleListSize = 3
        assertEquals(expectedScheduleListSize, actualScheduleListSize)
    }

    @Test
    fun getSchedulesIsNotBetweenTimePoint() {
        val schedule1 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 9, 11, 30, 0), LocalDateTime.of(2020, 10, 10, 11, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 9, 16, 0, 0), LocalDateTime.of(2020, 10, 10, 20, 30, 0),
                "645В")
        val schedule2 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 11, 9, 30, 0), LocalDateTime.of(2020, 10, 10, 16, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 11, 16, 0, 0), LocalDateTime.of(2020, 10, 10, 19, 30, 0),
                "645В")

        val scheduleList = arrayListOf<Schedule>(schedule1, schedule2)
        val trainNumber = 113

        scheduleService.scheduleMap[trainNumber] = scheduleList

        val startDataTime = LocalDateTime.of(2020, 10, 10, 10, 0, 0)
        val endDataTime = LocalDateTime.of(2020, 10, 10, 17, 0, 0)

        val expectedScheduleList = scheduleService.getSchedulesBetweenTimePoint(startDataTime, endDataTime)
        assertNotNull(expectedScheduleList)

        val actualScheduleListSize = expectedScheduleList?.size
        val expectedScheduleListSize = 0
        assertEquals(expectedScheduleListSize, actualScheduleListSize)
    }

    @Test
    fun getSchedulesBetweenTimePointWhenEndDateTimeIsNotRequired() {
        val schedule1 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 10, 11, 30, 0), LocalDateTime.of(2020, 10, 10, 11, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 10, 16, 0, 0), LocalDateTime.of(2020, 10, 10, 20, 30, 0),
                "645В")
        val schedule2 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 10, 9, 30, 0), LocalDateTime.of(2020, 10, 10, 16, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 10, 16, 0, 0), LocalDateTime.of(2020, 10, 10, 19, 30, 0),
                "645В")
        val schedule3 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 10, 16, 30, 0), LocalDateTime.of(2020, 10, 10, 9, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 10, 20, 0, 0), LocalDateTime.of(2020, 10, 10, 21, 30, 0),
                "645В")

        val scheduleList = arrayListOf<Schedule>(schedule1, schedule2, schedule3)
        val trainNumber = 113

        scheduleService.scheduleMap[trainNumber] = scheduleList

        val startDataTime = LocalDateTime.of(2020, 10, 10, 10, 0, 0)
        val endDataTime = null

        val expectedScheduleList = scheduleService.getSchedulesBetweenTimePoint(startDataTime, endDataTime)
        assertNotNull(expectedScheduleList)

        val actualScheduleListSize = expectedScheduleList?.size
        val expectedScheduleListSize = 3
        assertEquals(expectedScheduleListSize, actualScheduleListSize)
    }

    @Test
    fun getSchedulesIsNotBetweenTimePointWhenEndDateTimeIsNotRequired() {
        val schedule1 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 9, 11, 30, 0), LocalDateTime.of(2020, 10, 9, 11, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 9, 16, 0, 0), LocalDateTime.of(2020, 10, 9, 16, 0, 0),
                "645В")
        val schedule2 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 11, 9, 30, 0), LocalDateTime.of(2020, 10, 11, 9, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 11, 16, 0, 0), LocalDateTime.of(2020, 10, 11, 16, 0, 0),
                "645В")

        val scheduleList = arrayListOf<Schedule>(schedule1, schedule2)
        val trainNumber = 113

        scheduleService.scheduleMap[trainNumber] = scheduleList

        val startDataTime = LocalDateTime.of(2020, 10, 10, 10, 0, 0)
        val endDataTime = null

        val expectedScheduleList = scheduleService.getSchedulesBetweenTimePoint(startDataTime, endDataTime)
        assertNotNull(expectedScheduleList)

        val actualScheduleListSize = expectedScheduleList?.size
        val expectedScheduleListSize = 0
        assertEquals(expectedScheduleListSize, actualScheduleListSize)
    }

    @Test
    fun saveSchedule() {
        val schedule1 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 9, 11, 30, 0), LocalDateTime.of(2020, 10, 9, 11, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 9, 16, 0, 0), LocalDateTime.of(2020, 10, 9, 16, 0, 0),
                "645В")

        val trainNumber = 113

        scheduleService.saveSchedule(trainNumber, schedule1)

        assertEquals(scheduleService.scheduleMap.size, 1)
    }

    @Test
    fun sumTime() {
        val firstTermTime: LocalDateTime = LocalDateTime.of(2021, 5, 20, 4, 0, 0)
        val secondTermTime: LocalTime = LocalTime.of(1, 30, 0)
        val exceptedTime: LocalDateTime = scheduleService.sumTime(firstTermTime, secondTermTime)
        val actualTime: LocalDateTime = LocalDateTime.of(2021, 5, 20, 5, 30, 0)
        assertEquals(exceptedTime, actualTime)
    }

    @Test
    fun checkTimeOfStartWorkOfDriverWhenStartWorkTimeOfDriverIsBeforeDepartureTimeOfRollingStoke() {
        val startWorkTimeOfDriver = LocalDateTime.of(2021, 5, 20, 4, 0, 0)
        val departureTimeOfRollingStoke = LocalDateTime.of(2021, 5, 20, 5, 30, 0)
        val excepted: Boolean = scheduleService.checkTimeOfStartWorkOfDriver(startWorkTimeOfDriver, departureTimeOfRollingStoke)
        assertEquals(excepted, true)
    }

    @Test
    fun checkTimeOfStartWorkOfDriverWhenStartWorkTimeOfDriverIsAfterDepartureTimeOfRollingStoke() {
        val startWorkTimeOfDriver = LocalDateTime.of(2021, 5, 20, 5, 30, 0)
        val departureTimeOfRollingStoke = LocalDateTime.of(2021, 5, 20, 4, 0, 0)
        val excepted: Boolean = scheduleService.checkTimeOfStartWorkOfDriver(startWorkTimeOfDriver, departureTimeOfRollingStoke)
        assertEquals(excepted, false)
    }

    @Test
    fun checkTimeOfEndWorkOfDriverWhenEndWorkTimeOfDriverIsAfterArrivalTimeOfrRollingStoke() {
        val endWorkTimeOfDriver = LocalDateTime.of(2021, 5, 20, 5, 30, 0)
        val arrivalTimeOfrRollingStoke = LocalDateTime.of(2021, 5, 20, 4, 30, 0)
        val excepted: Boolean = scheduleService.checkTimeOfEndWorkOfDriver(endWorkTimeOfDriver, arrivalTimeOfrRollingStoke)
        assertEquals(excepted, true)
    }

    @Test
    fun checkTimeOfEndWorkOfDriverWhenEndWorkTimeOfDriverIsBeforeArrivalTimeOfrRollingStoke() {
        val endWorkTimeOfDriver = LocalDateTime.of(2021, 5, 20, 2, 30, 0)
        val arrivalTimeOfrRollingStoke = LocalDateTime.of(2021, 5, 20, 4, 0, 0)
        val excepted: Boolean = scheduleService.checkTimeOfEndWorkOfDriver(endWorkTimeOfDriver, arrivalTimeOfrRollingStoke)
        assertEquals(excepted, false)
    }
}