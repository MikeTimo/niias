package com.example.servicescheduleapp.model

data class RollingStock(
    var id: Int = 0,
    var model: String = "",
    var number: Int = 0,
    var isAvailable: Boolean = true
)
