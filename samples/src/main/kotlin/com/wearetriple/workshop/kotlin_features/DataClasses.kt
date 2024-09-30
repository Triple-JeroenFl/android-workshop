package com.wearetriple.workshop.kotlin_features

// A class generally used for storing data
data class User(val id: Int, val name: String)

fun main() {
    val john = User(id = 1, name = "John")

    // Data classes automatically have readable toString() functions
    println(john) // User(id = 1, name = John)

    // Data classes provide a copy() function
    val johnDuplicate = john.copy()
    val johnButDifferent = john.copy(id = 2)

    // Data classes automatically have equals() functions based on properties
    val jane = User(id = 3, name = "Jane")
    println(john == jane) // false
    println(john == johnDuplicate) // true
    println(john == johnButDifferent) // false
}