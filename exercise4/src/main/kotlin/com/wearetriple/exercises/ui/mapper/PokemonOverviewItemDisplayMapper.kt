package com.wearetriple.exercises.ui.mapper

import com.wearetriple.exercises.ui.model.display.PokemonOverviewItemDisplay
import com.wearetriple.exercises.ui.model.domain.PokemonOverviewItem
import javax.inject.Inject

class PokemonOverviewItemDisplayMapper @Inject constructor() {

    fun map(domain: PokemonOverviewItem): PokemonOverviewItemDisplay {
        return PokemonOverviewItemDisplay(
            id = domain.id,
            name = domain.name,
            imageUrl = domain.image,
            isFavorite = domain.isFavorite
        )
    }
}