package com.wearetriple.exercises.ui.mapper

import com.wearetriple.exercises.ui.model.domain.PokemonOverview
import com.wearetriple.exercises.ui.model.response.PokemonOverviewResponse
import javax.inject.Inject

class PokemonOverviewMapper @Inject constructor(
    private val pokemonOverviewItemMapper: PokemonOverviewItemMapper
) {

    fun map(
        response: PokemonOverviewResponse,
        batch: Int,
        storedFavoritesIds: List<String>
    ): PokemonOverview {
        return PokemonOverview(
            count = response.count ?: 0,
            batch = batch,
            results = response.results
                ?.mapNotNull { item -> item?.let { pokemonOverviewItemMapper.map(it, storedFavoritesIds) } }
                .orEmpty(),
        )
    }
}