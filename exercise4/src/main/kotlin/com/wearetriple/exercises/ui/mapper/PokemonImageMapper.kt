package com.wearetriple.exercises.ui.mapper

import javax.inject.Inject

class PokemonImageMapper @Inject constructor() {

    companion object {
        private const val SPRITE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/%s.png"
    }

    fun getSpriteURL(id: String): String = SPRITE_URL.format(id)
}