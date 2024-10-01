package com.wearetriple.exercises.ui.mapper

import com.wearetriple.exercises.ui.model.domain.PokemonOverviewItem
import com.wearetriple.exercises.ui.model.response.PokemonOverviewItemResponse
import javax.inject.Inject

class PokemonOverviewItemMapper @Inject constructor(
    private val imageMapper: PokemonImageMapper,
) {

    companion object {
        private val ID_REGEX = "(?<=/)\\d+(?=/)".toRegex()
    }

    fun map(entity: PokemonOverviewItemResponse, storedFavoritesIds: List<String>): PokemonOverviewItem? {
        val id = entity.url?.let(ID_REGEX::findAll)?.lastOrNull()?.value?.toIntOrNull() ?: return null
        val name = entity.name ?: return null
        return PokemonOverviewItem(
            id = id.toString(),
            name = name,
            image = imageMapper.getSpriteURL(id),
            isFavorite = storedFavoritesIds.contains(id.toString())
        )
    }
}