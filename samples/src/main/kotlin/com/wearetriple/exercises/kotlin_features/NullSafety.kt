package com.wearetriple.exercises.kotlin_features

fun main() {
    var neverNull: String = "This value cannot be null"
    /*neverNull = null // Does not compile*/

    var nullable: String? = "This value can be null"
    nullable = null // Perfectly fine

    var inferredNonNull = "The compiler assumes non-nullable"
    /*inferredNonNull = null // Does not compile*/

    val stringLength1: Int = stringLength(neverNull) // Perfectly fine
    /*val stringLength2: Int = stringLength(nullable) // Does not compile*/
    val stringLength3: Int? = nullableStringLength(neverNull) // Perfectly fine
    val stringLength4: Int? = nullableStringLength(nullable) // Perfectly fine
    /*val stringLength5: Int = nullableStringLength(nullable) // Does not compile*/
    val stringLength6: Int = nullableStringLength(nullable)!! // Not so perfectly fine
}

fun stringLength(notNullString: String): Int {
    return notNullString.length
}

fun nullableStringLength(nullableString: String?): Int? {
    return nullableString?.length // Notice the safe-call `?`
}