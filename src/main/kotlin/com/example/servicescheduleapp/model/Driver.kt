package com.example.servicescheduleapp.model

class Driver() {
    var id: Int = 0
    var number: Int = 0
    var firstName: String = ""
    var middleName: String = ""
    var lastName: String = ""
    var isAvailable: Boolean = true

    constructor(id: Int, number: Int, firstName: String, middleName: String, lastName: String, isAvailable: Boolean) : this() {
        this.id = id
        this.number = number
        this.firstName = firstName
        this.middleName = middleName
        this.lastName = lastName
        this.isAvailable = isAvailable
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Driver

        if (id != other.id) return false
        if (number != other.number) return false
        if (firstName != other.firstName) return false
        if (middleName != other.middleName) return false
        if (lastName != other.lastName) return false
        if (isAvailable != other.isAvailable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + number
        result = 31 * result + firstName.hashCode()
        result = 31 * result + middleName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + isAvailable.hashCode()
        return result
    }
}
