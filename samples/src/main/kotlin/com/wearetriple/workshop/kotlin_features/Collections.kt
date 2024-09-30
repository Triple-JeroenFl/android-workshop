package com.wearetriple.workshop.kotlin_features

fun main() {
    // Immutable (read only) list
    val persons = listOf("John", "Jane")
    println(persons.lastIndex) // 1
    println(persons.size) // 2
    println(persons.count()) // 2
    println(persons.first()) // John
    println(persons.last()) // Jane

    // Mutable list
    val dogs = mutableListOf("Woezel", "Pip", "Charlie")
    dogs.remove("Charlie")
    println(dogs.size) // 2
}