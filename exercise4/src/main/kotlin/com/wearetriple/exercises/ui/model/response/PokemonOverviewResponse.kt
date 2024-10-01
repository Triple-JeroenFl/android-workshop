package com.wearetriple.exercises.ui.model.response

data class PokemonOverviewResponse(
    val count: Int?,
    val next: String?,
    val results: List<PokemonOverviewItemResponse?>?,
)