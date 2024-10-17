package com.wearetriple.exercises.model.domain

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val isFavorite: Boolean,
)