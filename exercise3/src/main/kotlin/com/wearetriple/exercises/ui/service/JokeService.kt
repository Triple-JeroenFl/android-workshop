package com.wearetriple.exercises.ui.service

import com.wearetriple.exercises.ui.model.JokeResponse
import retrofit2.http.GET

interface JokeService {

    @GET("Any?blacklistFlags=nsfw,explicit&type=single")
    suspend fun getJoke(): JokeResponse
}