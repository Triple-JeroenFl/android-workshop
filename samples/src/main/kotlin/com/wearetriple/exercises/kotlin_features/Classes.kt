package com.wearetriple.exercises.kotlin_features

// Defining a class
class Customer

// Defining a class with properties in constructor and class body and a member function
class Contact(val id: Int, var email: String) {
    val category: String = "Work"

    // Member function
    fun printContact() {
        println("id: $id, email: $email, category: $category")
    }
}

// Instantiating a class
val contact = Contact(id = 1, email = "example@mail.com")

// Define an object class, which is always a singleton (only one instance exists)
object Singleton {

    fun doSomething() {
        // Implementation
    }
}

// Accessing and modifying class properties and calling class member functions
fun main() {
    val contact = Contact(id = 1, email = "example@mail.com")
    println(contact.email) // example@mail.com

    contact.email = "john.doe@mail.com"
    contact.printContact() // id: 1, email: john.doe@mail.com, category: work

    // Calling an object class; no instantiation necessary
    Singleton.doSomething()
}
