package com.wearetriple.exercises.ui.mapper

import javax.inject.Inject

class PokemonImageMapper @Inject constructor() {

    companion object {
        private const val SPRITE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/%d.png"
    }

    fun getSpriteURL(id: Int): String = SPRITE_URL.format(id)
}