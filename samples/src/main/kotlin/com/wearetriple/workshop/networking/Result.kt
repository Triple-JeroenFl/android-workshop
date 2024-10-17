package com.wearetriple.workshop.networking

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

suspend fun main() {
    // Moshi is a converter from Retrofit
    val converter = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()) // Factory for JSON to Kotlin data classes
        .build()
    // Create retrofit instance
    val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/")
        .addConverterFactory(MoshiConverterFactory.create(converter))
        .build()
    // Create a service from your API interface
    val service = retrofit.create(PokemonService::class.java)

    val mapper = PokemonListMapper()

    // Call from within a coroutine scope
    val result = Result.runCatching {
        service.getPokemonList()
    }

    when {
        result.isFailure -> {
            // Do something, maybe show error in UI?
            result.exceptionOrNull()?.printStackTrace()
        }
        result.isSuccess -> {
            val response: PokemonListResponse? = result.getOrNull()
            val pokemonList = response?.let { mapper.mapPokemonList(response) }
            println(pokemonList)
        }
    }
}