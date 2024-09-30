package com.wearetriple.exercises.kotlin_features

import java.time.LocalDate
import java.time.Period

// Can't change
val birthDate: LocalDate = LocalDate.parse("1969-04-20")

// Can change
var name: String = "Arie"

// Computed property
val age: Int
    get() = Period.between(birthDate, LocalDate.now()).years
