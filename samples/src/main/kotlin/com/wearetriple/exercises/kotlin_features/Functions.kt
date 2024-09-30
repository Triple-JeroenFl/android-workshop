package com.wearetriple.exercises.kotlin_features

import java.time.LocalDate
import java.time.Period

// Declaring a function
fun sayName() {
    // Calling a function with parameters
    val name = createName(firstName = "John", lastName = "Doe")
    println(name) // John Doe
}

// Function with a return type and arguments
fun createName(firstName: String, lastName: String): String {
    // String interpolation
    return "$firstName $lastName"
}

fun sayAgeNowAndNextYear() {
    val birthDay = LocalDate.parse("1969-04-20")
    val age = calculateYears(from = birthDay) // Only give `from` parameter
    val nextYear = LocalDate.now().plusYears(1)
    val ageNextYear = calculateYears(from = birthDay, to = nextYear) // Also give `to` parameter
    println("Age now: $age")
    println("Age next year: $ageNextYear")

    val years = birthDay.calculateYearsBetween(LocalDate.now()) // Call extension function
}

// Single expression function with a default argument
fun calculateYears(from: LocalDate, to: LocalDate = LocalDate.now()) = Period.between(from, to).years

// Extension function in the receiver context of a LocalDate where `this` is the LocalDate
fun LocalDate.calculateYearsBetween(to: LocalDate) = Period.between(this, to).years