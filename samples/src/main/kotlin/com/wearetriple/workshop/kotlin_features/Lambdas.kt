package com.wearetriple.workshop.kotlin_features

fun main() {
    // Function type with the `lambda` as definition
    val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
    val result = sum(1, 2)

    // Print result string in the parameter function type
    printClosure(closure = { result.toString() }) // 3
    // Lambda can be moved outside the parenthesis
    printClosure { result.toString() } // 3
}

// Function type as an argument
fun printClosure(closure: () -> String) {
    val text: String = closure()
    println(text)
}