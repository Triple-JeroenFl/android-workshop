package com.wearetriple.exercises.kotlin_features

// Define a sealed class (can also be a sealed interface)
sealed class Payment {
    data class CreditCard(val number: String, val expiryDate: String) : Payment()
    data class PayPal(val email: String) : Payment()
    data object Cash : Payment() // Data object can be used when it has no properties
}

// Sealed classes and interfaces can be used exhaustively for when-statements (no else needed)
fun processPayment(payment: Payment) {
    when (payment) {
        is Payment.CreditCard -> processCreditCardPayment(payment.number, payment.expiryDate)
        is Payment.PayPal -> processPayPalPayment(payment.email)
        is Payment.Cash -> processCashPayment()
    }
}

private fun processCreditCardPayment(number: String, expiryDate: String) {}
private fun processPayPalPayment(email: String) {}
private fun processCashPayment() {}