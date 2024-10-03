package com.wearetriple.exercises.ui.mapper

import com.wearetriple.exercises.ui.model.exception.BackendException
import com.wearetriple.exercises.ui.model.exception.ClientException
import com.wearetriple.exercises.ui.model.exception.ServerException
import retrofit2.Response
import javax.inject.Inject

class ResponseMapper @Inject constructor() {

    private companion object {
        const val TAG = "ResponseMapper"
        const val EMPTY_MESSAGE = "response was empty"
    }

    fun <T> map(response: Response<T>): Result<T> = runCatching {
        if (response.isSuccessful) {
            requireNotNull(response.body()) {
                "$TAG - $EMPTY_MESSAGE"
            }
        } else {
            when {
                response.code() in 400..<500 -> throw ClientException(response.message())
                response.code() >= 500 -> throw ServerException(response.message())
                else -> throw BackendException(response.message())
            }
        }
    }
}