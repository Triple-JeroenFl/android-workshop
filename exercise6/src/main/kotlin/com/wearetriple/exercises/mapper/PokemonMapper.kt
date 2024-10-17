package com.wearetriple.exercises.mapper

import com.wearetriple.exercises.model.domain.Pokemon
import com.wearetriple.exercises.model.response.PokemonListItemResponse
import javax.inject.Inject

class PokemonMapper @Inject constructor() {

    fun map(response: PokemonListItemResponse, favoriteIds: List<Int>): Pokemon {
        val id = response.url.split("/").dropLast(1).last().toInt()
        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
        return Pokemon(
            id = id,
            name = response.name,
            imageUrl = imageUrl,
            isFavorite = id in favoriteIds
        )
    }
}
