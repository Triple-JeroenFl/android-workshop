package com.wearetriple.workshop.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun processSomething(): String { // suspend keyword indicates it must be called from within a CoroutineScope
    // Delay will block the coroutine, notice the suspend function call icon on the left side in your IDE.
    delay(timeMillis = 5000) // Can only be called from within a CoroutineScope
    return "something"
}

fun main() {
//    processSomething() // Won't compile!

    val scope = CoroutineScope(Dispatchers.Main) // Create a CoroutineScope
    scope.launch { // Use `launch` to kick-off your coroutine
        // Suspend functions can be called in here
        processSomething()
    }
}