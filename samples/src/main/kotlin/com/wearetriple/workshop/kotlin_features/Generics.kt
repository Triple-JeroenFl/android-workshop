package com.wearetriple.workshop.kotlin_features

// Generics in a function where T is of type Greeter
fun <T : Greeter> sayGreeting(greeter: T) {
    println(greeter.getGreeting())
}

// Generics in a class where T is of type Greeter
class GreetingService<T : Greeter> {

    fun sayGreeting(greeter: T) {
        println(greeter.getGreeting())
    }
}

fun main() {
    val cat = Cat()
    val person = Person()
    sayGreeting(greeter = person) // Hello
    sayGreeting(greeter = cat) // Meow

    val greetingService = GreetingService<Cat>()
    greetingService.sayGreeting(greeter = cat) // Meow
    /*greetingService.sayGreeting(greeter = person) // Does not compile*/
}