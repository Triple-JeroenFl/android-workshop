package com.wearetriple.exercises.kotlin_features

fun main() {
    // For loop over a list
    val persons = listOf("John", "Jane", "Jonathan", "Jennifer")
    for (person in persons) {
        println(person)
    }

    // For loop over a range
    for (index in 0..persons.lastIndex) {
        println(persons[index])
    }

    // While loop
    var cakesEaten = 0
    while (cakesEaten < 3) {
        println("Eat a cake")
        cakesEaten++
    }
}