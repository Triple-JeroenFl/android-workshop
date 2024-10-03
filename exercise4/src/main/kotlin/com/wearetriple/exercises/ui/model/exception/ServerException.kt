package com.wearetriple.exercises.ui.model.exception

data class ServerException(
    override val message: String
): Exception(message)
