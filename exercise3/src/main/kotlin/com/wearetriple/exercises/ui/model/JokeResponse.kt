package com.wearetriple.exercises.ui.model

data class JokeResponse(
    val error: Boolean,
    val category: String,
    val type: String,
    val joke: String,
    val flags: JokeFlagsResponse,
    val id: Int,
    val safe: Boolean,
    val lang: String
)
