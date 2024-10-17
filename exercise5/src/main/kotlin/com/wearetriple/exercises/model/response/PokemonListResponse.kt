package com.wearetriple.exercises.model.response

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonListItemResponse>,
)