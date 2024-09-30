package com.wearetriple.workshop.kotlin_features

fun main() {
    // If statement
    val user = User(id = 1, name = "John")
    val variable1 = if (user.name == "John") {
        doThis()
    } else {
        doThat()
    }

    // Shorthand if statement
    val variable2 = if (user.name == "John") doThis() else doThat()

    // When statement
    val variable3 = when(user.name) {
        "John" -> doThis()
        "Jane" -> doThat()
        else -> doSomethingElse()
    }
}

private fun doThis(): String = ""
private fun doThat(): String = ""
private fun doSomethingElse(): String = ""