package com.wearetriple.workshop.networking

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/*
* Read more at https://square.github.io/retrofit/
*/

// Check response schema
/*
{
  "type": String,
  "setup": String,
  "punchline": String,
  "id": Int
}
 */

// Define response model
data class JokeResponse(
    val id: Int,
    val type: String,
    val setup: String,
    val punchline: String,
)

// Define API interface
interface JokeService {

    // Define a request method
    @GET("random_joke")
    suspend fun getJoke(): JokeResponse
}

suspend fun main() {
    // Moshi is a converter from Retrofit
    val converter = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()) // Factory for JSON to Kotlin data classes
        .build()
    // Create retrofit instance
    val retrofit = Retrofit.Builder()
        .baseUrl("http://www.official-joke-api.appspot.com/")
        .addConverterFactory(MoshiConverterFactory.create(converter))
        .build()
    // Create a service from your API interface
    val service = retrofit.create(JokeService::class.java)

    // Call from within a coroutine scope
    val joke = service.getJoke()
    println(joke)
}