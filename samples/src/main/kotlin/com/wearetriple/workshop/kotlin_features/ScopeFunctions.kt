package com.wearetriple.workshop.kotlin_features

fun main() {
    // `let` has the context as argument `it` and returns the result of the lambda
    val a = "test".let {
        println(it) // test
        it.plus("1") // the result of the lambda
    }
    println(a) // test1

    // `let` is often used to execute code containing non-null values
    val b: String? = "Hello"
//    processNonNullString(b) // compilation error: b can be null
    val length = b?.let {
        processNonNullString(it) // OK: 'it' is not null inside '?.let { }'
        it.length // the result of the lambda
    }

    // `with` has the context as receiver `this` and returns the result of the lambda
    val numbers = mutableListOf("one", "two", "three")
    val firstAndLast = with(numbers) {
        "The first element is ${first()}," + // `first` can be called directly because the context is `this`
                " the last element is ${last()}"
    }
    println(firstAndLast)

    // `run` has the context as receiver `this` and returns the result of the lambda
    val firstAndLast1 = numbers.run {
        "The first element is ${first()}," + // essentially the same as `with` but as an extension function
                " the last element is ${last()}"
    }

    // `also` has the context as argument `it` and returns the object itself
    // read as: "and also do the following with the object"
    numbers
        .also { println("The list elements before adding new one: $it") }
        .add("four")

    // `apply` has the context as receiver `this` and returns the object itself
    // often used as configurator, read as: "apply the following assignments to the object"
    val adam = Persona("Adam").apply {
        age = 32
        city = "London"
    }
    println(adam)
}

private data class Persona(val name: String, var age: Int = 0, var city: String? = null )

private fun processNonNullString(str: String) {}