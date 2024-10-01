package com.wearetriple.exercises.ui.model.domain

data class PokemonOverview(
    val count: Int,
    val batch: Int,
    val results: List<PokemonOverviewItem>,
)
