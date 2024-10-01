package com.wearetriple.exercises.ui.service

import com.wearetriple.exercises.ui.model.response.PokemonOverviewResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun getAllPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Response<PokemonOverviewResponse>
}