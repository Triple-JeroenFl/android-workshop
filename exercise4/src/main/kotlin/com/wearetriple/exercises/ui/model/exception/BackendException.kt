package com.wearetriple.exercises.ui.model.exception

data class BackendException(
    override val message: String
) : Exception(message)
