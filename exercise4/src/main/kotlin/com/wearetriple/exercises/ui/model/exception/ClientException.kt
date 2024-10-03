package com.wearetriple.exercises.ui.model.exception

data class ClientException(
    override val message: String
) : Exception(message)
