package com.wearetriple.exercises.kotlin_features

// Define an enum class
enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

// Define an enum class with properties
enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

fun main() {
    // When statement on an enum class
    val direction = Direction.EAST
    when(direction) {
        Direction.NORTH -> walkNorth()
        Direction.SOUTH -> walkSouth()
        Direction.WEST -> walkWest()
        Direction.EAST -> walkEast()
    }

    // Use property of an enum class
    setColor(Color.RED.rgb)
}

private fun setColor(rgb: Int) {}

private fun walkNorth() {}
private fun walkSouth() {}
private fun walkWest() {}
private fun walkEast() {}