package com.example.servicescheduleapp.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.PropertySource


@ConstructorBinding
@ConfigurationProperties(prefix = "app")
data class StationScheduleConfig(var stationName: Array<String>, var durationOfTrainStop: String, var durationOfBlock: String) {
}