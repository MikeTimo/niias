package com.example.restservice

import com.example.restservice.controller.Controller
import com.example.restservice.model.Schedule
import com.example.restservice.service.ScheduleService
import com.example.restservice.utils.Helper
import com.example.restservice.utils.ScheduleTest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime


@RunWith(SpringRunner::class)
@WebMvcTest(Controller::class)
@TestPropertySource("/application-test.yml")
internal class ControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var scheduleService: ScheduleService

    @Before
    fun addFirstElement() {
        val departureTime: LocalDateTime = LocalDateTime.now()
        val arrivalTime = departureTime.plusHours(1).plusMinutes(30)

        val schedule = Schedule(
            1, 1,
            4, departureTime, 10, arrivalTime
        )

        scheduleService.saveSchedule(schedule)

        println(scheduleService.scheduleMap.size)
    }

    @Test
    fun get() {
        val helper = Helper()
        val expected = helper.getSchedule(1)

        val mvcResult =
            mockMvc.perform(MockMvcRequestBuilders.get("/schedule?data=2010-04-21&trainNumber=1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk).andReturn()

        val status = mvcResult.response.status

        val contentAsString = mvcResult.response.contentAsString

        assertEquals("Неверный статус ответа", HttpStatus.OK.value(), status);

        val objectMapper: ObjectMapper = ObjectMapper()
        val actual = objectMapper.readValue(contentAsString, ScheduleTest::class.java)

        assertEquals("Возвращается неправильный результат при запросе GET /schedule с параметрами trainNumber и data.", expected, actual)
    }

    @Test
    fun post() {
        val exampleScheduleJson =
            "{\"trainNumber\":5,\"driverNumber\":4,\"departureStation\":1,\"departureTime\":\"2021-04-25T13:03:27.392\",\"arrivalStation\":5,\"arrivalTime\":\"2021-04-25T14:33:27.392\"}"


        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post("/schedule").accept(MediaType.APPLICATION_JSON).content(exampleScheduleJson)
                .contentType(
                    MediaType.APPLICATION_JSON
                )
        ).andReturn()

        val response: MockHttpServletResponse = mvcResult.response

        assertEquals(HttpStatus.OK.value(), response.status)
    }
}