package com.wearetriple.workshop.kotlin_features

// Defining an interface
interface Greeter {
    fun getGreeting(): String
}

// Implementing an interface
class Person : Greeter {
    override fun getGreeting() = "Hello"
}

class Cat : Greeter {
    override fun getGreeting() = "Meow"
}

fun main() {
    val cat = Cat()
    println(cat is Greeter) // true
    println(cat.getGreeting()) // Meow

    val person = Person()
    println(person is Greeter) // true
    println(person.getGreeting()) // Hello
}