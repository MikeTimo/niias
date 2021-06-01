package com.example.servicescheduleapp.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app-stations")
data class StationsProperties(val stations: List<String>, var durationOfTrainStop: String, val durationOfBlock: String, val timeLap: String,
                              val countOfLap: String)