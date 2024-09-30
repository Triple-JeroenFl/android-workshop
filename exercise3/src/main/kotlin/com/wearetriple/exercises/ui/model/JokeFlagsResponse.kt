package com.wearetriple.exercises.ui.model

data class JokeFlagsResponse(
    val nsfw: Boolean,
    val religious: Boolean,
    val political: Boolean,
    val racist: Boolean,
    val sexist: Boolean,
    val explicit: Boolean
)
