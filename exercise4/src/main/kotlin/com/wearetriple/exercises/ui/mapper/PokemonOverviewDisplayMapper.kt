package com.wearetriple.exercises.ui.mapper

import com.wearetriple.exercises.ui.model.display.PokemonOverviewDisplay
import com.wearetriple.exercises.ui.model.domain.PokemonOverview
import javax.inject.Inject

class PokemonOverviewDisplayMapper @Inject constructor(
    private val itemMapper: PokemonOverviewItemDisplayMapper
) {

    fun map(domain: PokemonOverview): PokemonOverviewDisplay {
        return PokemonOverviewDisplay(
            list = domain.results.map { item ->
                itemMapper.map(item)
            }
        )
    }
}