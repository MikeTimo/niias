package com.example.servicescheduleapp.config

import com.example.servicescheduleapp.model.Schedule

data class TrainDriverScheduleConfig(val IdOfTrain: Int, val driverNumber: Int, val scheduleList: MutableList<Schedule>) {
}