package com.wearetriple.workshop.repository

import com.wearetriple.workshop.error.ApiException
import com.wearetriple.workshop.error.PokemonService
import com.wearetriple.workshop.networking.PokemonListResponse
import com.wearetriple.workshop.networking.PokemonResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // @Singleton because it is a common practice to only have a single instance of each repository
class PokemonRepository @Inject constructor(
    private val service: PokemonService
) {

    suspend fun getPokemonList(): Result<PokemonListResponse> {
        return runCatching {
            val response = service.getPokemonList()
            if (response.isSuccessful) {
                response.body()!!
            } else {
                throw ApiException(code = response.code())
            }
        }
    }

    suspend fun getPokemon(id: Int): Result<PokemonResponse> {
        return runCatching {
            val response = service.getPokemon(id)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                throw ApiException(code = response.code())
            }
        }
    }
}