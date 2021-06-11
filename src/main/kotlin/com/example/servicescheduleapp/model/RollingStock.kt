package com.example.servicescheduleapp.model

class RollingStock() {
    var id: Int = 0
    var model: String = ""
    var number: Int = 0
    var isAvailable: Boolean = true

    constructor(id: Int, model: String, number: Int, isAvailable: Boolean): this() {
        this.id = id
        this.model = model
        this.number = number
        this.isAvailable = isAvailable
    }
}
