package com.example.servicescheduleapp

import com.example.servicescheduleapp.controller.ScheduleController
import com.example.servicescheduleapp.model.Schedule
import com.example.servicescheduleapp.service.ScheduleService
import com.google.gson.Gson
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.*


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.*
import org.springframework.test.web.servlet.MockMvc
import java.time.LocalDateTime
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ScheduleController::class)
class ScheduleControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var scheduleService: ScheduleService


    @Test
    fun getTrainSchedule() {
        val trainNumber = 113
        val schedule1 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 10, 16, 30, 0), LocalDateTime.of(2020, 10, 10, 16, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 10, 17, 0, 0), LocalDateTime.of(2020, 10, 10, 16, 30, 0),
                "645В")
        val schedule2 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 10, 16, 30, 0), LocalDateTime.of(2020, 10, 10, 16, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 10, 17, 0, 0), LocalDateTime.of(2020, 10, 10, 16, 30, 0),
                "645В")

        val scheduleList = arrayListOf<Schedule>(schedule1, schedule2)

        given(scheduleService.getScheduleOnDayByTrain(trainNumber)).willReturn(scheduleList)

        mockMvc.perform(get("/schedule/get").param("trainNumber", trainNumber.toString())).andExpect(status().isOk)

    }

    @Test
    fun getSchedulesWhenEndDateTimeIsRequired() {
        val schedule1 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 10, 16, 30, 0), LocalDateTime.of(2020, 10, 10, 11, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 10, 16, 0, 0), LocalDateTime.of(2020, 10, 10, 20, 30, 0),
                "645В")
        val schedule2 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 10, 16, 30, 0), LocalDateTime.of(2020, 10, 10, 16, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 10, 18, 0, 0), LocalDateTime.of(2020, 10, 10, 19, 30, 0),
                "645В")
        val schedule3 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 10, 16, 30, 0), LocalDateTime.of(2020, 10, 10, 9, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 10, 14, 0, 0), LocalDateTime.of(2020, 10, 10, 21, 30, 0),
                "645В")

        val scheduleList = arrayListOf<Schedule>(schedule1, schedule2, schedule3)

        val startDataTime = LocalDateTime.of(2020, 10, 10, 10, 0, 0)
        val endDataTime = LocalDateTime.of(2020, 10, 10, 17, 0, 0)

        given(scheduleService.getSchedulesBetweenTimePoint(startDataTime, endDataTime)).willReturn(scheduleList)

        mockMvc.perform(get("/schedule/get-list").param("startDataTime", startDataTime.toString()).param("endDataTime", endDataTime.toString())).andExpect(status().isOk)
    }

    @Test
    fun getSchedulesWhenEndDateTimeIsNotRequired() {
        val schedule1 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 10, 16, 30, 0), LocalDateTime.of(2020, 10, 10, 16, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 10, 16, 0, 0), LocalDateTime.of(2020, 10, 10, 20, 30, 0),
                "645В")
        val schedule2 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 10, 16, 30, 0), LocalDateTime.of(2020, 10, 10, 16, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 10, 18, 0, 0), LocalDateTime.of(2020, 10, 10, 19, 30, 0),
                "645В")
        val schedule3 = Schedule(1, 8001, 22551, 15, 21, 1, "Андроновка Оп", "Андроновка Оп",
                LocalDateTime.of(2020, 10, 10, 16, 30, 0), LocalDateTime.of(2020, 10, 10, 16, 30, 0), "Андроновка Оп", "Андроновка Оп", LocalDateTime.of(2020, 10, 10, 19, 0, 0), LocalDateTime.of(2020, 10, 10, 21, 30, 0),
                "645В")

        val scheduleList = arrayListOf<Schedule>(schedule1, schedule2, schedule3)

        val startDataTime = LocalDateTime.of(2020, 10, 10, 10, 0, 0)

        given(scheduleService.getSchedulesBetweenTimePoint(startDataTime, null)).willReturn(scheduleList)

        mockMvc.perform(get("/schedule/get-list").param("startDataTime", startDataTime.toString())).andExpect(status().isOk)
    }
}