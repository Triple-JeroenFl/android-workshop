package com.wearetriple.workshop.networking

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

// Check response schema: https://pokeapi.co/docs/v2#resource-listspagination-section
/*
{
   "count": Int,
   "next": String?,
   "previous": String?,
   "results": List
}
*/

// Define response models
data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResponse>,
)

data class PokemonResponse(
    val name: String,
    val url: String,
)

// Define business logic entity
data class Pokemon(
    val id: Int, // Where do I get this from?
    val name: String,
    val imageUrl: String, // Where do I get this from?
)

// Define API service
interface PokemonService {

    // Define a request method
    @GET("v2/pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20, // Size of response list
        @Query("offset") offset: Int = 0, // Start index of response list
    ): PokemonListResponse
}

// Define your mapper
class PokemonListMapper @Inject constructor() {

    fun mapPokemonList(response: PokemonListResponse): List<Pokemon> {
        val pokemon = mutableListOf<Pokemon>()
        for (pokemonResponse in response.results) {
            // Take id from url: `"url":"https://pokeapi.co/api/v2/pokemon/1/"`
            val id = pokemonResponse.url.split("/").dropLast(1).last().toInt()
            // Image url can be found in the specific pokemon response url (v2/pokemon/1/) but easier to do like so:
            val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
            pokemon += Pokemon(
                id = id,
                name = pokemonResponse.name,
                imageUrl = imageUrl
            )
        }
        return pokemon

        // Can be simplified with `map` function on the results collection to directly map each item:
        return response.results.map { pokemonResponse ->
            // Take id from url: `"url":"https://pokeapi.co/api/v2/pokemon/1/"`
            val id = pokemonResponse.url.split("/").dropLast(1).last().toInt()
            // Image url can be found in the specific pokemon response url (v2/pokemon/1/) but easier to do like so:
            val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
            Pokemon(
                id = id,
                name = pokemonResponse.name,
                imageUrl = imageUrl
            )
        }
    }
}

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
    val pokemonResponse = service.getPokemonList()

    // Map response to entity
    val pokemonList = mapper.mapPokemonList(pokemonResponse)

    println(pokemonList)
}
