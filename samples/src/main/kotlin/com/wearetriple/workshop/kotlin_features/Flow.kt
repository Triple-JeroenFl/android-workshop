package com.wearetriple.workshop.kotlin_features

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun countFlow(count: Int): Flow<Int> = flow {
    // Everything inside this lambda is suspended, and suspend function can be called
    println("Flow started")
    for (i in 1..count) {
        delay(100)
        emit(i)
    }
}

fun main() = runBlocking {
    println("Calling simple function...")
    val flow = countFlow(count = 3)
    println("Calling collect...")
    flow.collect { value -> println(value) } // call from within coroutine scope
    println("Calling collect again...")
    flow.collect { value -> println(value) } // call from within coroutine scope
}

/*
Flows are 'cold'. Which means the `flow` does not execute until it is collected.
*/