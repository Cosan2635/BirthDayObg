package com.example.birthdayobg.models

import java.io.Serializable

data class Person(
    var id: Int,
    var name: String,
    var age: Int,
    var birthYear: Int,
    var birthMonth: Int,
    var birthDayOfMonth: Int,
    var userId: String
) : Serializable {
    constructor(
        name: String,
        age: Int,
        birthYear: Int,
        birthMonth: Int,
        birthDayOfMonth: Int,
        email: String
    ) : this(
        -1, name, age, birthYear, birthMonth, birthDayOfMonth, email
    )

    override fun toString(): String {
        return "$id $name $age $birthYear $birthMonth $birthDayOfMonth $userId"
    }
}